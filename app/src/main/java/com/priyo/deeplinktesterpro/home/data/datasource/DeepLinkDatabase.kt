package com.priyo.deeplinktesterpro.home.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.priyo.deeplinktesterpro.home.data.model.DeepLink

@Database(entities = [DeepLink::class], version = 1)
abstract class DeepLinkDatabase : RoomDatabase() {
    abstract fun deepLinkDao(): DeepLinkDao
}