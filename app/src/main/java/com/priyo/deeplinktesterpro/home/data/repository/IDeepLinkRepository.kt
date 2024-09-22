package com.priyo.deeplinktesterpro.home.data.repository

import com.priyo.deeplinktesterpro.home.data.model.DeepLink

interface IDeepLinkRepository {

    suspend fun insertDeepLink(deepLink: DeepLink)

    suspend fun updateDeepLink(deepLink: DeepLink)

    suspend fun getDeepLinks(): List<DeepLink>?

    suspend fun deleteDeepLink(deepLinkId: Int)
}