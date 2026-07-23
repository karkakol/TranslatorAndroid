package com.karolkakol.translator.ui.screens.benchmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BenchmarkScreen(viewModel: BenchmarkViewModel = viewModel()) {
    val name by viewModel.name.collectAsStateWithLifecycle()
    Box(Modifier.fillMaxSize().background(Color.Blue)) {
        Text(name)
    }
}
