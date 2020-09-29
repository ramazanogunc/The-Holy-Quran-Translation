package com.ramo.quran.helper

class LocaleHelper {

    fun getLocaleId(language: String): Int {
        return if (language == "tr") 1 else 2
    }

    fun getLocaleTag(id: Int): String {
        return if (id == 1) "tr" else "en"
    }

}