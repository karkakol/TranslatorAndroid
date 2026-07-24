package com.karolkakol.translator.ui.screens.translation

import androidx.compose.animation.animateBounds
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldLabelScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karolkakol.translator.ui.theme.TranslatorTheme

@Composable
fun TranslationScreen(
    modifier: Modifier = Modifier,
    viewModel: TranslationViewModel = viewModel(),
) {
    if (viewModel.areTranslatorsReady.collectAsStateWithLifecycle().value) {
        TranslationContent(
            modifier = modifier,
            fromTextFieldState = viewModel.fromTextFieldState,
            toTextFieldState = viewModel.toTextFieldState,
            fromTranslationType = viewModel.fromTranslationType.collectAsStateWithLifecycle().value,
            changeTranslatorType = viewModel::changeTranslatorType,
        )
    } else {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator(
                Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                strokeWidth = 20.dp,
            )
        }
    }
}

@Composable
private fun TranslationContent(
    fromTextFieldState: TextFieldState,
    toTextFieldState: TextFieldState,
    fromTranslationType: AppTranslatorType,
    changeTranslatorType: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        TranslationTextField(
            state = fromTextFieldState,
            placeholder = { Text("Translate text", style = MaterialTheme.typography.titleLarge) },
        )
        HorizontalDivider(
            Modifier
                .padding(horizontal = 36.dp)
                .padding(vertical = 16.dp),
        )
        TranslationTextField(
            state = toTextFieldState,
            readOnly = true,
            placeholder = { Text("Translation...", style = MaterialTheme.typography.titleLarge) },
        )
        Spacer(Modifier.height(24.dp))
        TranslationSelectLanguageRow(
            fromTranslationType,
            changeTranslatorType,
        )
    }
}

@Composable
fun TranslationSelectLanguageRow(
    fromTranslationType: AppTranslatorType,
    changeTranslatorType: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSwapped = fromTranslationType == AppTranslatorType.initialTo

    val fromCard =
        remember {
            movableContentWithReceiverOf<LookaheadScope, Modifier> { modifier ->
                TranslationLanguageCard(
                    AppTranslatorType.initialFrom.name,
                    modifier.animateBounds(this),
                )
            }
        }

    val toCard =
        remember {
            movableContentWithReceiverOf<LookaheadScope, Modifier> { modifier ->
                TranslationLanguageCard(
                    AppTranslatorType.initialTo.name,
                    modifier.animateBounds(this),
                )
            }
        }
    LookaheadScope {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (isSwapped) toCard(Modifier.weight(1f)) else fromCard(Modifier.weight(1f))

            IconButton(onClick = changeTranslatorType) {
                val rotation by animateFloatAsState(
                    targetValue = if (isSwapped) 180f else 0f,
                    animationSpec = spring(stiffness = Spring.StiffnessLow),
                    label = "IconRotation",
                )
                Icon(
                    Icons.Default.SwapHoriz,
                    contentDescription = "Change languages",
                    modifier =
                        Modifier.graphicsLayer(
                            rotationZ = rotation,
                        ),
                )
            }

            if (isSwapped) fromCard(Modifier.weight(1f)) else toCard(Modifier.weight(1f))
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
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
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
fun TranslationLanguageCard(
    text: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier.padding(12.dp)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.titleLarge,
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
            toTextFieldState = TextFieldState("Witaj świecie"),
            fromTranslationType = AppTranslatorType.English,
            changeTranslatorType = {},
        )
    }
}
