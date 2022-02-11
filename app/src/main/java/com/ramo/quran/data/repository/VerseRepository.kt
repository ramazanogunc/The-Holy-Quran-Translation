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

    suspend fun getVerse(surahNumber: Int, verseNumber: Int) =
        verseDao.getVerse(surahNumber, verseNumber)

    suspend fun searchVerse(text: String, page: Int, perPage: Int) =
        verseDao.searchVerse(text, page, perPage)

    fun searchVerse(text: String) = verseDao.searchVerse(text)
}

