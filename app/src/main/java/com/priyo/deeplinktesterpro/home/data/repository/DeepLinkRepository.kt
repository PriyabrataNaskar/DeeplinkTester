package com.priyo.deeplinktesterpro.home.data.repository

import com.priyo.deeplinktesterpro.home.data.model.DeepLink
import com.priyo.deeplinktesterpro.home.data.datasource.DeepLinkDao
import javax.inject.Inject

class DeepLinkRepository @Inject constructor(
    private val deepLinkDao: DeepLinkDao
) : IDeepLinkRepository {

    override suspend fun insertDeepLink(deepLink: DeepLink) {
        deepLinkDao.insetDeepLink(deepLink)
    }

    override suspend fun getDeepLinks(): List<DeepLink>? {
        return deepLinkDao.getDeepLinks()
    }

    override suspend fun deleteDeepLink(deepLinkId: Int) {
        deepLinkDao.deleteDeepLink(deepLinkId = deepLinkId)
    }
}