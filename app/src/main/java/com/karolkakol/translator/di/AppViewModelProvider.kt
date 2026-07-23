package com.karolkakol.translator.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.karolkakol.translator.ui.screens.translation.TranslationViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TranslationViewModel()
        }

    }
}