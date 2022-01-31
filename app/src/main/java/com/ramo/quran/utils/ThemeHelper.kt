package com.ramo.quran.utils

import androidx.appcompat.app.AppCompatDelegate
import com.ramo.quran.model.AppTheme

object ThemeHelper {

    fun setThemeMode(appTheme: AppTheme) {
        AppCompatDelegate.setDefaultNightMode(getThemeMode(appTheme))
    }

    private fun getThemeMode(appTheme: AppTheme): Int = when (appTheme) {
        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}