package com.karolkakol.translator.ui.screens.benchmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BenchmarkScreen(viewModel: BenchmarkViewModel = viewModel()) {
    val polishSentences = viewModel.polishSentencesState.collectAsStateWithLifecycle().value

    Column(Modifier.fillMaxSize()) {
        Text("Polish Sentences", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp).padding(12.dp))
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(polishSentences, key = { index, item -> "$index-$item" }) { index, item ->
                Text(
                    "$index: $item",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(12.dp),
                )
            }
        }
    }
}
