package com.ramo.quran.helper

import android.content.Context
import android.content.SharedPreferences
import com.ramo.quran.R

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

    fun changeSelectedFont(fontName: String, fontResourceId: Int) {
        getEditor().putInt("fontResourceId", fontResourceId).also { it.apply() }
        getEditor().putString("fontName", fontName).also { it.apply() }
    }

    fun getCurrentFontName() = sharedPreferences.getString("fontName", "roboto")
    fun getCurrentFontResourceId(): Int {
        val number = sharedPreferences.getInt("fontResourceId", 0)
        return if (number != 0) number else R.font.roboto
    }

    fun getReadPosition(): Int = sharedPreferences.getInt("readVersePosition", 0)
    fun setReadPosition(position: Int) {
        getEditor().putInt("readVersePosition", position).also { it.apply() }
    }

    fun isKeepScreenOn(): Boolean = sharedPreferences.getBoolean("isKeepOnScreen", false)
    fun setIsKeepOnScreen(isKeepScreenOn: Boolean) {
        getEditor().putBoolean("isKeepOnScreen", isKeepScreenOn).also { it.apply() }
    }

    private fun getEditor() = sharedPreferences.edit()


}