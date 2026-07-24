package com.karolkakol.translator.ui.screens.translation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class TranslationViewModel : ViewModel() {
    val fromTextFieldState = TextFieldState("")
    val toTextFieldState = TextFieldState("")

    init {
        viewModelScope.launch {
            snapshotFlow { fromTextFieldState.text.toString() }
                .debounce(400.milliseconds)
                .distinctUntilChanged()
                .collect { text ->
                    if (text.isNotBlank()) {
                        translateTextField(text)
                    } else {
                        clearTranslatedTextField()
                    }
                }
        }
    }

    fun clearTranslatedTextField() {
        toTextFieldState.setTextAndPlaceCursorAtEnd("")
    }

    fun translateTextField(text: String) {
        toTextFieldState.setTextAndPlaceCursorAtEnd(text.reversed())
    }
}
