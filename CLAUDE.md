# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Native Android app (Kotlin + Jetpack Compose) for a master's thesis on translation. Single `:app` module. The app is early-stage scaffolding: a `Translation` screen and a `Benchmark` screen (currently placeholders with red/green backgrounds) reachable via a navigation drawer — the intended direction is on-device translation plus a benchmarking view.

## Commands

Uses the Gradle wrapper (`./gradlew`). Gradle 9.5, AGP 9.3, Kotlin 2.2.10, JDK 11 target.

- Build debug APK: `./gradlew assembleDebug`
- Install on a connected device/emulator: `./gradlew installDebug`
- Lint: `./gradlew lint`
- Unit tests (JVM, `src/test`): `./gradlew testDebugUnitTest`
- Single unit test: `./gradlew testDebugUnitTest --tests "com.karolkakol.translator.ExampleUnitTest"`
- Instrumented tests (`src/androidTest`, needs a device/emulator): `./gradlew connectedDebugAndroidTest`

## Architecture

- **UI**: 100% Jetpack Compose, Material 3. `MainActivity` sets `TranslatorTheme { AppNavigationHost() }`. `TranslatorApplication` is an empty `Application` subclass.
- **Navigation**: Uses **Navigation 3** (`androidx.navigation3`), not the older `NavHost`/`NavController`. Destinations are `@Serializable object`s implementing `NavKey` (e.g. `TranslationKey`, `BenchmarkKey`). `AppNavigationHost` holds a `rememberNavBackStack` and renders via `NavDisplay` + `entryProvider`. Drawer navigation clears the back stack and pushes a single key (tab-like behavior, not additive).
- **ViewModels & DI**: Manual DI — no Hilt/Koin. `AppViewModelProvider.Factory` (a `viewModelFactory` with `initializer` blocks) is the single source of truth for constructing ViewModels, wired in by overriding `MainActivity.defaultViewModelProviderFactory`. Screens obtain their ViewModel via `viewModel()`, which resolves through that factory. **New ViewModels must be registered in `AppViewModelProvider`.**
- **State**: ViewModels expose `StateFlow`; screens consume with `collectAsState()`.
- **Package layout** (under `com.karolkakol.translator`): `di/` (ViewModel factory), `ui/navigation/` (nav keys + host), `ui/screens/<feature>/` (a `*Screen` composable + its `*ViewModel`), `ui/theme/`.

## Conventions

- **Explicit backing fields** are enabled via the `-Xexplicit-backing-fields` compiler flag. This is why ViewModels declare a public read-only type with a private mutable backing field like:
  ```kotlin
  val nameState: StateFlow<String>
      field = MutableStateFlow("...")
  ```
  Prefer this pattern over the older `_name`/`name` two-property idiom.
- Dependencies are managed through the version catalog `gradle/libs.versions.toml` — add/bump libraries there, reference via `libs.*`.
