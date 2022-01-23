package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.VerseDao
import javax.inject.Inject

class VerseRepository @Inject constructor(
    private val verseDao: VerseDao
) {
    suspend fun getCurrentSurahVerses(surahNumber: Int) =
        verseDao.getCurrentSurahVerses(surahNumber)
}

