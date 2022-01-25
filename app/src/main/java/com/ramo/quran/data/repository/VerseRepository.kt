package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.VerseDao
import com.ramo.quran.data.shared_pref.AppSharedPref
import javax.inject.Inject

class VerseRepository @Inject constructor(
    private val verseDao: VerseDao,
    private val appSharedPref: AppSharedPref
) {
    suspend fun getCurrentSurahVerses() =
        verseDao.getCurrentSurahVerses(appSharedPref.currentSurah)
}
