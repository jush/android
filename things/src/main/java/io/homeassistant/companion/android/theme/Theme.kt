package io.homeassistant.companion.android.theme

import androidx.compose.runtime.Composable
import androidx.compose.material.MaterialTheme

@Composable
fun WearAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = wearColorPalette,
        content = content
    )
}
