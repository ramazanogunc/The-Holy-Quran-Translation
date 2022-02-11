package com.ramo.quran.model.db_translation

data class SurahWithVersicleNumbers(
    val surahName: SurahName,
    val versicleNumbers: List<Int>
)