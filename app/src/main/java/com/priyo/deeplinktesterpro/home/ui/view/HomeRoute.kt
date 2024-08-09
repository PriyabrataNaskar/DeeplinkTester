package com.priyo.deeplinktesterpro.home.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyo.deeplinktesterpro.home.ui.utils.copyToClipboard
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute() {
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeViewModel>()

    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            is HomeSideEffect.DeepLinkSaved -> {}
            is HomeSideEffect.CopyDeepLinkToClipBoard -> context.copyToClipboard(it.deeplink)
        }
    }

    HomeScreen(
        state = state,
        saveDeeplink = {
            viewModel.saveDeepLink(it)
        },
        copyDeepLinkToClipBoard = {
            viewModel.copyDeepLinkToClipBoard(it)
        },
        deleteDeepLink = { item, index ->
            viewModel.deleteDeepLink(item, index)
        }
    )

}
