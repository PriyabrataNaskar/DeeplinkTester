package com.priyo.deeplinktesterpro.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
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

    fun saveDeepLink(deepLink: String) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            deepLinkRepository.insertDeepLink(DeepLink(deepLink))
            val results = deepLinkRepository.getDeepLinks()
            reduce {
                state.copy(deepLinks = results ?: state.deepLinks, isLoading = false, isError = null)
            }
        }
    }

    fun deleteDeepLink(deepLink: DeepLink, index: Int) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            deepLinkRepository.deleteDeepLink(deepLink.id)
            val results = deepLinkRepository.getDeepLinks()
            reduce {
                state.copy(deepLinks = results ?: emptyList(), isLoading = false, isError = null)
            }
        }
    }

    fun copyDeepLinkToClipBoard(deepLink: String) = intent {
        postSideEffect(HomeSideEffect.CopyDeepLinkToClipBoard(deepLink))
    }

    private fun getDeepLinks() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val results = deepLinkRepository.getDeepLinks()
            if (!results.isNullOrEmpty()) {
                reduce {
                    state.copy(deepLinks = results, isLoading = false, isError = null)
                }
            }
        }
    }

}

//create room db and store deeplink
class DeepLinkRepository(private val deepLinkDao: DeepLinkDao) {

    suspend fun insertDeepLink(deepLink: DeepLink) {
        deepLinkDao.insetDeepLink(deepLink)
    }

    suspend fun getDeepLinks(): List<DeepLink>? {
        return deepLinkDao.getDeepLinks()
    }

    suspend fun deleteDeepLink(deepLinkId: Int) {
        deepLinkDao.deleteDeepLink(deepLinkId = deepLinkId)
    }
}

@Database(entities = [DeepLink::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deepLinkDao(): DeepLinkDao
}

//create room db and store deeplink
@Database(entities = [DeepLink::class], version = 1)
abstract class DeepLinkDatabase : RoomDatabase() {
    abstract fun deepLinkDao(): DeepLinkDao
}


@Dao
interface DeepLinkDao {

    @Query("SELECT * FROM deeplinks")
    suspend fun getDeepLinks(): List<DeepLink>?

    @Query("DELETE FROM deeplinks WHERE id = :deepLinkId")
    suspend fun deleteDeepLink(deepLinkId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetDeepLink(deepLink: DeepLink)
}

@Entity(tableName = "deeplinks")
data class DeepLink(
    val deepLink: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)