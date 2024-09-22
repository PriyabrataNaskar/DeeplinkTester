package com.priyo.deeplinktesterpro.home.data.di

import android.content.Context
import androidx.room.Room
import com.priyo.deeplinktesterpro.home.data.datasource.AppDatabase
import com.priyo.deeplinktesterpro.home.data.datasource.DeepLinkDao
import com.priyo.deeplinktesterpro.home.data.datasource.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideDeeplinkDao(
        @ApplicationContext appContext: Context
    ): DeepLinkDao {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "my_database"
        ).addMigrations(MIGRATION_1_2).build().deepLinkDao()
    }
}