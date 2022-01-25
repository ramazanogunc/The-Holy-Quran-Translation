package com.ramo.quran.data.shared_pref

import android.content.Context
import android.content.SharedPreferences
import com.ramo.quran.R
import com.ramo.quran.core.ext.get
import com.ramo.quran.core.ext.set
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    companion object {
        private const val CURRENT_SURAH = "currentSurah"
        private const val FONT_SIZE = "fontSize"
        private const val IS_FIRST_LOGIN = "isFirstLogin"
        private const val FONT_RESOURCE_ID = "fontResourceId"
        private const val FONT_NAME = "fontName"
        private const val READ_VERSE_POSITION = "readVersePosition"
        private const val IS_KEEP_SCREEN_ON = "isKeepOnScreen"
    }


    var currentSurah: Int
        get() = sharedPreferences.get<Int>(CURRENT_SURAH) ?: 1
        set(value) = sharedPreferences.set(CURRENT_SURAH, value)

    var fontSize: Float
        get() = sharedPreferences.get<Float>(FONT_SIZE) ?: 16f
        set(value) = sharedPreferences.set(FONT_SIZE, value)

    var isFirstLogin: Boolean
        get() = sharedPreferences.get<Boolean>(IS_FIRST_LOGIN) ?: true
        set(value) = sharedPreferences.set(IS_FIRST_LOGIN, value)


    fun changeSelectedFont(fontName: String, fontResourceId: Int) {
        sharedPreferences.set(FONT_RESOURCE_ID, fontResourceId)
        sharedPreferences.set(FONT_NAME, fontName)
    }

    fun getCurrentFontName(): String = sharedPreferences.get<String>(FONT_NAME) ?: "roboto"
    fun getCurrentFontResourceId(): Int =
        sharedPreferences.get<Int>(FONT_RESOURCE_ID) ?: R.font.roboto

    var readPosition: Int
        get() = sharedPreferences.get<Int>(READ_VERSE_POSITION) ?: 0
        set(value) = sharedPreferences.set(READ_VERSE_POSITION, value)

    var isKeepScreenOn: Boolean
        get() = sharedPreferences.get<Boolean>(IS_KEEP_SCREEN_ON) ?: false
        set(value) = sharedPreferences.set(IS_KEEP_SCREEN_ON, value)


}