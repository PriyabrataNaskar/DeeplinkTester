package com.priyo.deeplinktesterpro.home.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.priyo.deeplinktesterpro.home.data.model.DeepLink

@Database(entities = [DeepLink::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deepLinkDao(): DeepLinkDao
}

val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE deeplinks ADD COLUMN time INTEGER" )
    }
}