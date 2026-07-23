package com.karolkakol.translator.ui.screens.translation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TranslationScreen(
    modifier: Modifier = Modifier,
    viewModel: TranslationViewModel = viewModel(),
) {
    val viewModelName by viewModel.nameState.collectAsState()

    Box(Modifier
        .fillMaxSize()
        .background(Color.Red)) {
        Text(viewModelName)
    }
}