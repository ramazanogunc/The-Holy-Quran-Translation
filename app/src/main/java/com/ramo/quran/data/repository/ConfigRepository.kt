package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.ConfigDao
import javax.inject.Inject

class ConfigRepository @Inject constructor(
    private val configDao: ConfigDao
) {
    suspend fun getConfig() = configDao.getConfig()
    suspend fun updateCurrentResource(resourceId: Int) = configDao.updateCurrentResource(resourceId)
    suspend fun changeLocaleWithResource(languageId: Int) =
        configDao.changeLocaleWithResource(languageId)
}