package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.ResourceDao
import com.ramo.quran.model.db_translation.ResourceWithLanguage
import javax.inject.Inject

class ResourceRepository @Inject constructor(
    private val resourceDao: ResourceDao
) {
    suspend fun getAll(): List<ResourceWithLanguage> = resourceDao.getAllResourcesWithLanguage()
}