package io.homeassistant.companion.android.home.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text
import io.homeassistant.companion.android.common.data.integration.Entity
import io.homeassistant.companion.android.theme.WearAppTheme
import io.homeassistant.companion.android.util.previewEntity1
import io.homeassistant.companion.android.util.previewEntity2
import io.homeassistant.companion.android.common.R as commonR

private const val TAG = "EntityListView"
@ExperimentalComposeUiApi
@Composable
fun EntityViewList(
    entityLists: Map<String, List<Entity<*>>>,
    entityListsOrder: List<String>,
    entityListFilter: (Entity<*>) -> Boolean,
    onEntityClicked: (String, String) -> Unit,
    isHapticEnabled: Boolean,
    isToastEnabled: Boolean
) {
    Log.d(
        TAG,
        "EntityViewList() called with: entityLists = ${entityLists.isEmpty()}, entityListsOrder = $entityListsOrder, entityListFilter = $entityListFilter, onEntityClicked = $onEntityClicked, isHapticEnabled = $isHapticEnabled, isToastEnabled = $isToastEnabled"
    )
    entityLists.forEach { entityList ->
        Log.d(TAG, "EntityList: ${entityList.key}")
        entityList.value.forEach { entity -> Log.d(TAG, "Entity: $entity") }
    }
    // Remember expanded state of each header
    val expandedStates = remember {
        mutableStateMapOf<Int, Boolean>().apply {
            entityLists.forEach {
                put(it.key.hashCode(), true)
            }
        }
    }

    val scalingLazyListState: LazyListState = rememberLazyListState()
    LocalView.current.requestFocus()

    WearAppTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 24.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 48.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scalingLazyListState
        ) {
            for (header in entityListsOrder) {
                val entities = entityLists[header].orEmpty()
                if (entities.isNotEmpty()) {
                    item {
                        if (entityLists.size > 1) {
                            ListHeader(
                                string = header,
                                expanded = expandedStates[header.hashCode()]!!,
                                onExpandChanged = { expandedStates[header.hashCode()] = it }
                            )
                        } else {
                            ListHeader(header)
                        }
                    }
                    if (expandedStates[header.hashCode()]!!) {
                        val filtered = entities.filter { entityListFilter(it) }
                        items(filtered.size) { index ->
                            EntityUi(
                                filtered[index],
                                onEntityClicked,
                                isHapticEnabled,
                                isToastEnabled
                            )
                        }

                        if (filtered.isNullOrEmpty()) {
                            item {
                                Column {
                                    Chip(
                                        label = {
                                            Text(
                                                text = stringResource(commonR.string.loading_entities),
                                                textAlign = TextAlign.Center
                                            )
                                        },
                                        onClick = { /* No op */ },
                                        colors = ChipDefaults.primaryChipColors()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalWearMaterialApi
@Preview
@Composable
private fun PreviewEntityListView() {
    EntityViewList(
        entityLists = mapOf(stringResource(commonR.string.lights) to listOf(previewEntity1, previewEntity2)),
        entityListsOrder = listOf(stringResource(commonR.string.lights)),
        entityListFilter = { true },
        onEntityClicked = { _, _ -> },
        isHapticEnabled = false,
        isToastEnabled = false
    )
}
