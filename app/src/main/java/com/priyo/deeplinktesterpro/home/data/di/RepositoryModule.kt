package com.priyo.deeplinktesterpro.home.data.di

import com.priyo.deeplinktesterpro.home.data.repository.DeepLinkRepository
import com.priyo.deeplinktesterpro.home.data.repository.IDeepLinkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindDeeplinkRepositoryProviders(
        deepLinkRepository: DeepLinkRepository,
    ): IDeepLinkRepository
}