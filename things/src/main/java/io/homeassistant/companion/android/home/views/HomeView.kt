package io.homeassistant.companion.android.home.views

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.homeassistant.companion.android.home.MainViewModel
import io.homeassistant.companion.android.theme.WearAppTheme
import io.homeassistant.companion.android.common.R as commonR

private const val DEBUG_VIEW = "debug"
private const val SCREEN_LANDING = "landing"
private const val SCREEN_ENTITY_LIST = "entity_list"
private const val SCREEN_SETTINGS = "settings"
private const val SCREEN_SET_FAVORITES = "set_favorites"

private const val TAG = "HomeView"
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun LoadHomePage(
    mainViewModel: MainViewModel
) {
    var shortcutEntitySelectionIndex: Int by remember { mutableStateOf(0) }
    val context = LocalContext.current
    LocalView.current.requestFocus()

    WearAppTheme {
        if (mainViewModel.entities.isNullOrEmpty() && mainViewModel.favoriteEntityIds.isNullOrEmpty()) {
            Column {
                ListHeader(id = commonR.string.loading)
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 10.dp, end = 10.dp),
                    text = {
                        Text(
                            text = stringResource(commonR.string.loading_entities),
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = { /* No op */ },
                )
            }
        } else {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = SCREEN_LANDING
            ) {
                composable(DEBUG_VIEW) {
                    Box(
                        modifier = Modifier
                            .background(Color.Cyan)
                            .fillMaxSize()
                    ) {

                    }
                }
                composable(SCREEN_LANDING) {
                    MainView(
                        mainViewModel,
                        mainViewModel.favoriteEntityIds,
                        { id, state -> mainViewModel.toggleEntity(id, state) },
                        { navController.navigate(SCREEN_SETTINGS) },
                        { lists, order, filter ->
                            mainViewModel.entityLists.clear()
                            mainViewModel.entityLists.putAll(lists)
                            mainViewModel.entityListsOrder.clear()
                            mainViewModel.entityListsOrder.addAll(order)
                            mainViewModel.entityListFilter = filter
                            navController.navigate(SCREEN_ENTITY_LIST)
                        },
                        mainViewModel.isHapticEnabled.value,
                        mainViewModel.isToastEnabled.value,
                        { id -> mainViewModel.removeFavorites(id) }
                    )
                }
                composable(SCREEN_ENTITY_LIST) {
                    EntityViewList(
                        entityLists = mainViewModel.entityLists,
                        entityListsOrder = mainViewModel.entityListsOrder,
                        entityListFilter = mainViewModel.entityListFilter,
                        onEntityClicked =
                        { entityId, state ->
                            mainViewModel.toggleEntity(entityId, state)
                        },
                        isHapticEnabled = mainViewModel.isHapticEnabled.value,
                        isToastEnabled = mainViewModel.isToastEnabled.value,
                        onBack = {
                            Log.d(TAG, "onBack pressed")
                            navController.navigate(SCREEN_LANDING)
                        }
                    )
                }
                composable(SCREEN_SETTINGS) {
/*
                    SettingsView(
                        favorites = mainViewModel.favoriteEntityIds,
                        onClickSetFavorites = { swipeDismissableNavController.navigate(SCREEN_SET_FAVORITES) },
                        onClearFavorites = { mainViewModel.clearFavorites() },
                        onClickSetShortcuts = { swipeDismissableNavController.navigate(SCREEN_SET_TILE_SHORTCUTS) },
                        onClickLogout = { mainViewModel.logout() },
                        isHapticEnabled = mainViewModel.isHapticEnabled.value,
                        isToastEnabled = mainViewModel.isToastEnabled.value,
                        onHapticEnabled = { mainViewModel.setHapticEnabled(it) },
                        onToastEnabled = { mainViewModel.setToastEnabled(it) },
                        onClickTemplateTile = { swipeDismissableNavController.navigate(SCREEN_SET_TILE_TEMPLATE) }
                    )
*/
                }
                composable(SCREEN_SET_FAVORITES) {
/*
                    SetFavoritesView(
                        mainViewModel,
                        mainViewModel.favoriteEntityIds
                    ) { entityId, position, isSelected ->
                        val favorites = Favorites(entityId, position)
                        if (isSelected) {
                            mainViewModel.addFavorites(favorites)
                        } else {
                            mainViewModel.removeFavorites(entityId)
                        }
                    }
*/
                }
            }
        }
    }
}
