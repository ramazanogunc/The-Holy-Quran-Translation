package com.ramo.quran.utils

class LocaleHelper {

    fun getLocaleId(language: String): Int {
        return if (language == "tr") 1 else 2
    }
}