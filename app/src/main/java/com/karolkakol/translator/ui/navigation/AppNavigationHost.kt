package com.karolkakol.translator.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.karolkakol.translator.ui.screens.benchmark.BenchmarkScreen
import com.karolkakol.translator.ui.screens.translation.TranslationScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object TranslationKey : NavKey

@Serializable
object BenchmarkKey : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(TranslationKey)
    val currentKey = backStack.last()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Translation App",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 12.dp),
                )
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                NavigationDrawerItem(
                    label = { Text("Translation") },
                    selected = currentKey == TranslationKey,
                    icon = { Icon(Icons.Default.Language, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        if (currentKey !is TranslationKey) {
                            backStack.clear()
                            backStack.add(TranslationKey)
                        }
                    },
                )
                NavigationDrawerItem(
                    label = { Text("Benchmark") },
                    selected = currentKey is BenchmarkKey,
                    icon = { Icon(Icons.Default.BarChart, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        if (currentKey !is BenchmarkKey) {
                            backStack.clear()
                            backStack.add(BenchmarkKey)
                        }
                    },
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            when (currentKey) {
                                is TranslationKey -> "Translation"
                                else -> "Benchmark"
                            },
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                        }
                    },
                )
            },
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider =
                    entryProvider {
                        entry<TranslationKey> {
                            TranslationScreen()
                        }
                        entry<BenchmarkKey> {
                            BenchmarkScreen()
                        }
                    },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
