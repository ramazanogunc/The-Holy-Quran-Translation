package com.ramo.quran.model

data class Surah (
    val id : Int,
    val name: String,
    val surahNo: Int,
    val versicles :List<Versicle>,
    var previousSurahName: String,
    var nextSurahName: String
)