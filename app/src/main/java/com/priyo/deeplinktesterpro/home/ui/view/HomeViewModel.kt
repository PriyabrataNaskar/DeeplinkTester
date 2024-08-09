package com.priyo.deeplinktesterpro.home.ui.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.priyo.deeplinktesterpro.home.data.datasource.AppDatabase
import com.priyo.deeplinktesterpro.home.data.repository.DeepLinkRepository
import com.priyo.deeplinktesterpro.home.data.model.DeepLink
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext val applicationContext: Context
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    private val database = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "my_database"
    ).build()

    init {
        getDeepLinks()
    }

    private val deepLinkRepository = DeepLinkRepository(database.deepLinkDao())

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
