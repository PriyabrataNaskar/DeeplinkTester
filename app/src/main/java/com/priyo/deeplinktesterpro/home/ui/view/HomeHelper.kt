package com.priyo.deeplinktesterpro.home.ui.view

import com.priyo.deeplinktesterpro.home.data.model.DeepLink

data class HomeState(
    val deepLinks: List<DeepLink> = emptyList(),
    val isLoading: Boolean = false,
    val isError: String? = null,
)

sealed class HomeSideEffect {
    data class DeepLinkSaved(val deeplink: String) : HomeSideEffect()
    data class CopyDeepLinkToClipBoard(val deeplink: String) : HomeSideEffect()
}