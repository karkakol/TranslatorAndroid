package com.karolkakol.translator.ui.screens.translation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

enum class AppTranslatorType {
    Polish,
    English,
}

class TranslationViewModel : ViewModel() {
    val fromTextFieldState = TextFieldState("")
    val toTextFieldState = TextFieldState("")
    val englishPolishTranslator =
        Translation.getClient(
            TranslatorOptions
                .Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.POLISH)
                .build(),
        )
    val polishEnglishTranslator =
        Translation.getClient(
            TranslatorOptions
                .Builder()
                .setSourceLanguage(TranslateLanguage.POLISH)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build(),
        )

    var isEnglishTranslatorReady = false
    var isPolishTranslatorReady = false

    var areTranslatorsReady = MutableStateFlow(false)
    var fromTranslationType = MutableStateFlow(AppTranslatorType.English)
    var toTranslationType = MutableStateFlow(AppTranslatorType.Polish)

    init {
        viewModelScope.launch {
            setupTranslators()
        }

        viewModelScope.launch {
            snapshotFlow { fromTextFieldState.text.toString() }
                .debounce(400.milliseconds)
                .distinctUntilChanged()
                .collect { text ->
                    if (text.isBlank()) {
                        clearTranslatedTextField()
                    } else {
                        translateTextField(text)
                    }
                }
        }
    }

    fun setupTranslators() {
        downloadModel(englishPolishTranslator, AppTranslatorType.Polish)
        downloadModel(polishEnglishTranslator, AppTranslatorType.English)
    }

    fun downloadModel(
        translator: Translator,
        translatorType: AppTranslatorType,
    ) {
        val conditions = DownloadConditions.Builder().requireWifi().build()

        translator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                when (translatorType) {
                    AppTranslatorType.English -> isEnglishTranslatorReady = true
                    AppTranslatorType.Polish -> isPolishTranslatorReady = true
                }
                if (isPolishTranslatorReady && isEnglishTranslatorReady) {
                    areTranslatorsReady.value = true
                }
            }
    }

    fun changeTranslatorType() {
        val tempTranslationType = fromTranslationType.value
        fromTranslationType.value = toTranslationType.value
        toTranslationType.value = tempTranslationType
        val temp = fromTextFieldState.text.toString()
        fromTextFieldState.setTextAndPlaceCursorAtEnd(toTextFieldState.text.toString())
        toTextFieldState.setTextAndPlaceCursorAtEnd(temp)
    }

    fun clearTranslatedTextField() {
        toTextFieldState.setTextAndPlaceCursorAtEnd("")
    }

    fun translateTextField(text: String) {
        val translator =
            if (fromTranslationType.value == AppTranslatorType.English) englishPolishTranslator else polishEnglishTranslator
        translator.translate(text).addOnSuccessListener { translation ->
            toTextFieldState.setTextAndPlaceCursorAtEnd(translation)
        }
    }
}
