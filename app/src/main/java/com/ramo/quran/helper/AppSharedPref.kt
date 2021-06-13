package com.ramo.quran.helper

import android.content.Context
import android.content.SharedPreferences

class AppSharedPref(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    fun getCurrentSurah(): Int {
        val number = sharedPreferences.getInt("currentSurah", 0)
        return if (number != 0) number else 1
    }

    fun changeCurrentSurah(surahNumber: Int) {
        getEditor().putInt("currentSurah", surahNumber).also { it.apply() }
    }

    fun getFontSize(): Float {
        val number = sharedPreferences.getFloat("fontSize", 0f)
        return if (number != 0f) number else 15f
    }

    fun changeFontSize(fontSize: Float) {
        getEditor().putFloat("fontSize", fontSize).also { it.apply() }
    }

    fun isFirstLogin(): Boolean = sharedPreferences.getBoolean("isFirstLogin", true)
    fun setIsFirstLogin(firstLogin: Boolean) {
        getEditor().putBoolean("isFirstLogin", firstLogin).also { it.apply() }
    }

    private fun getEditor() = sharedPreferences.edit()
}