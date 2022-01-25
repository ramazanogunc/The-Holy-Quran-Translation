package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.SurahNameDao
import com.ramo.quran.data.shared_pref.AppSharedPref
import javax.inject.Inject

class SurahNameRepository @Inject constructor(
    private val surahNameDao: SurahNameDao,
    private val appSharedPref: AppSharedPref
) {
    suspend fun getCurrentSurahName() =
        surahNameDao.getCurrentSurahName(appSharedPref.currentSurah)

    suspend fun getAllSurahName() = surahNameDao.getAllSurahName()
}

