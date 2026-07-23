package com.karolkakol.translator.ui.screens.benchmark

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class BenchmarkViewModel : ViewModel() {
    var name = MutableStateFlow<String>("Benchmark")
}