package com.karolkakol.translator.ui.screens.translation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TranslationViewModel: ViewModel() {
    val nameState : StateFlow<String>
    field = MutableStateFlow("Translation View Model")
}