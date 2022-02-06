package com.ramo.quran.model

import com.ramo.quran.model.db_translation.SurahName

data class SurahWithVersicleNumbers(
    val surahName: SurahName,
    val versicleNumbers: List<Int>
)