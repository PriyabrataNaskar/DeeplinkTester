package com.priyo.deeplinktesterpro.home.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.priyo.deeplinktesterpro.home.data.model.DeepLink

@Dao
interface DeepLinkDao {

    @Query("SELECT * FROM deeplinks Order by time asc")
    suspend fun getDeepLinks(): List<DeepLink>?

    @Update
    suspend fun updateDeepLink(deepLink: DeepLink)

    @Query("DELETE FROM deeplinks WHERE id = :deepLinkId")
    suspend fun deleteDeepLink(deepLinkId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetDeepLink(deepLink: DeepLink)
}