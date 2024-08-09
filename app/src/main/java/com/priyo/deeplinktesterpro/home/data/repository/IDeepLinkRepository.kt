package com.priyo.deeplinktesterpro.home.data.repository

import com.priyo.deeplinktesterpro.home.data.model.DeepLink
import com.priyo.deeplinktesterpro.home.data.datasource.DeepLinkDao

//create room db and store deeplink
interface IDeepLinkRepository {

    suspend fun insertDeepLink(deepLink: DeepLink)

    suspend fun getDeepLinks(): List<DeepLink>?

    suspend fun deleteDeepLink(deepLinkId: Int)
}