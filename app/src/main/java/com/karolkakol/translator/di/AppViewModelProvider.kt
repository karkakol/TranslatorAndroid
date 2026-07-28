package com.karolkakol.translator.di

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.karolkakol.translator.TranslatorApplication
import com.karolkakol.translator.ui.screens.benchmark.BenchmarkViewModel
import com.karolkakol.translator.ui.screens.translation.TranslationViewModel

object AppViewModelProvider {
    val Factory =
        viewModelFactory {
            initializer {
                TranslationViewModel()
            }

            initializer {
                val application = (this[APPLICATION_KEY] as TranslatorApplication)

                BenchmarkViewModel(application)
            }
        }
}
