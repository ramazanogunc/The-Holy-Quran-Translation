package com.ramo.quran.data.repository

import com.ramo.quran.data.dao.SurahNameDao
import com.ramo.quran.data.dao.VerseDao
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.model.SurahWithVersicleNumbers
import javax.inject.Inject

class SurahNameRepository @Inject constructor(
    private val surahNameDao: SurahNameDao,
    private val verseDao: VerseDao,
    private val appSharedPref: AppSharedPref
) {
    suspend fun getCurrentSurahName() =
        surahNameDao.getCurrentSurahName(appSharedPref.currentSurah)

    suspend fun getAllSurahName() = surahNameDao.getAllSurahName()

    suspend fun getAllSurahWithVersicleNumbers(): List<SurahWithVersicleNumbers> {
        val surahNames = surahNameDao.getAllSurahName()
        val versicleNumbers = verseDao.getAllSurahVerseNumbers()
        val surahWithVersicleNumbers = mutableListOf<SurahWithVersicleNumbers>()
        for (i in surahNames.indices) {
            surahWithVersicleNumbers.add(
                SurahWithVersicleNumbers(
                    surahName = surahNames[i],
                    versicleNumbers = (0 until versicleNumbers[i]).map { it }
                )
            )
        }
        return surahWithVersicleNumbers
    }
}

