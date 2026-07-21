package com.karolkakol.translator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.karolkakol.translator.di.AppViewModelProvider
import com.karolkakol.translator.ui.navigation.AppNavigationHost
import com.karolkakol.translator.ui.theme.TranslatorTheme

class MainActivity : ComponentActivity() {
    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = AppViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                AppNavigationHost()
            }
        }
    }
}
