package com.karolkakol.translator.ui.screens.benchmark

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.karolkakol.translator.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BenchmarkViewModel(
    application: Application,
) : AndroidViewModel(application) {
    val polishSentencesState: StateFlow<List<String>>
        field = MutableStateFlow(emptyList())

    init {
        readPolishSentences()
    }

    fun readPolishSentences() {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>()

            val polishSentences =
                context.resources
                    .openRawResource(R.raw.pl_short)
                    .bufferedReader()
                    .use { it.readText() }
                    .split("\n")
                    .drop(1)
                    .filter { it.isNotBlank() }
                    .toList()

            Log.i("XXX", polishSentences.joinToString { "\n" })

            withContext(Dispatchers.Main) {
                polishSentencesState.value = polishSentences
            }
        }
    }
}
