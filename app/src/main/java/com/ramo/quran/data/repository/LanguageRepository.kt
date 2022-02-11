package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.LanguageDao
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val languageDao: LanguageDao
) {
    suspend fun getAllLanguages() = languageDao.getAllLanguages()
    suspend fun getCurrentLanguage() = languageDao.getCurrentLanguage()
}

