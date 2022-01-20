package io.homeassistant.companion.android.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.homeassistant.companion.android.common.R as commonR

@Composable
fun ListHeader(
    stringId: Int,
    expanded: Boolean,
    onExpandChanged: (Boolean) -> Unit
) {
    ListHeader(stringResource(stringId), expanded, onExpandChanged)
}

@Composable
fun ListHeader(
    string: String,
    expanded: Boolean,
    onExpandChanged: (Boolean) -> Unit
) {
    ListHeader(
        modifier = Modifier
            .clickable { onExpandChanged(!expanded) }
    ) {
        Row {
            Text(
                text = string + if (expanded) "\u2001-" else "\u2001+"
            )
        }
    }
}

@Composable
fun ListHeader(id: Int, modifier: Modifier = Modifier) {
    ListHeader(stringResource(id), modifier)
}

@Composable
fun ListHeader(string: String, modifier: Modifier = Modifier) {
    ListHeader {
        Row {
            Text(
                text = string,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun PreviewListHeader() {
    ListHeader(
        stringId = commonR.string.other,
        expanded = true,
        onExpandChanged = {}
    )
}


// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:wear/compose/compose-material/src/commonMain/kotlin/androidx/wear/compose/material/ListHeader.kt
@Composable
 fun ListHeader(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colors.onSurface,
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.height(48.dp)
            .fillMaxWidth()
            .wrapContentSize()
            .background(backgroundColor)
            .padding(horizontal = 14.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides MaterialTheme.typography.button,
            content = content
        )
    }
}