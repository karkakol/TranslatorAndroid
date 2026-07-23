package com.karolkakol.translator.ui.screens.translation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldLabelScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karolkakol.translator.ui.theme.TranslatorTheme

@Composable
fun TranslationScreen(
    modifier: Modifier = Modifier,
    viewModel: TranslationViewModel = viewModel(),
) {
    TranslationContent(
        modifier = modifier,
        fromTextFieldState = viewModel.fromTextFieldState,
        toTextFieldState = viewModel.toTextFieldState,
    )
}

@Composable
private fun TranslationContent(
    fromTextFieldState: TextFieldState,
    toTextFieldState: TextFieldState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        TranslationTextField(
            state = fromTextFieldState,
            label = { Text("Translate text") },
            placeholder = { Text("Translate text", style = MaterialTheme.typography.titleLarge) },
        )
        HorizontalDivider(
            Modifier
                .padding(horizontal = 36.dp)
                .padding(vertical = 16.dp)
        )
        TranslationTextField(
            state = toTextFieldState,
            readOnly = true,
            placeholder = { Text("Translation...", style = MaterialTheme.typography.titleLarge) },
        )
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            TranslationLanguageCard("English", Modifier.weight(1f))
            IconButton(onClick = {}) {
                Icon(Icons.Default.SwapHoriz, contentDescription = "Change languages")
            }
            TranslationLanguageCard("Polish", Modifier.weight(1f))
        }
    }
}

@Composable
fun TranslationTextField(
    state: TextFieldState,
    placeholder: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null
) {
    TextField(
        state = state,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        lineLimits = TextFieldLineLimits.MultiLine(3, 6),
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun TranslationLanguageCard(text: String, modifier: Modifier = Modifier) {
    Card(modifier.padding(12.dp)) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TranslationScreenPreview() {
    TranslatorTheme {
        TranslationContent(
            fromTextFieldState = TextFieldState("Hello world"),
            toTextFieldState = TextFieldState("dlrow olleH")
        )
    }
}
