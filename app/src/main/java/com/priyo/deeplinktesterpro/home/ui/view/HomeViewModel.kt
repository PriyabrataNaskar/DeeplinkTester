package com.priyo.deeplinktesterpro.home.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyo.deeplinktesterpro.home.data.model.DeepLink
import com.priyo.deeplinktesterpro.home.data.repository.IDeepLinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deepLinkRepository: IDeepLinkRepository,
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        getDeepLinks()
    }

    private var deepLinks: List<DeepLink>? = emptyList()

    private fun getDeepLinks() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            deepLinks = deepLinkRepository.getDeepLinks()
            deepLinks?.let {
                reduce {
                    state.copy(deepLinks = it, isLoading = false, isError = null)
                }
            }
        }
    }

    private fun doesContains(deepLink: String): Boolean =
        deepLinks?.map { it.deepLink }?.contains(deepLink) ?: false

    fun saveDeepLink(deepLink: String) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            if (doesContains(deepLink).not()) {
                deepLinkRepository.insertDeepLink(DeepLink(deepLink))
            }
            deepLinks = deepLinkRepository.getDeepLinks()
            reduce {
                state.copy(
                    deepLinks = deepLinks ?: state.deepLinks,
                    isLoading = false,
                    isError = null
                )
            }
        }
    }

    fun deleteDeepLink(deepLink: DeepLink, index: Int) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            deepLinkRepository.deleteDeepLink(deepLink.id)
            deepLinks = deepLinkRepository.getDeepLinks()
            reduce {
                state.copy(deepLinks = deepLinks ?: emptyList(), isLoading = false, isError = null)
            }
        }
    }

    fun copyDeepLinkToClipBoard(deepLink: String) = intent {
        postSideEffect(HomeSideEffect.CopyDeepLinkToClipBoard(deepLink))
    }

}
