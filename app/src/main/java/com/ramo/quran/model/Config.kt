package com.ramo.quran.model

data class Config(
    val textSize:Int,
    val language: Language,
    val resource: Resource? = null
)