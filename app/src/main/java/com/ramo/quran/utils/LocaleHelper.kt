package com.ramo.quran.utils

object LocaleHelper {

    fun getLocaleId(language: String): Int {
        return if (language == "tr") 1 else 2
    }
}