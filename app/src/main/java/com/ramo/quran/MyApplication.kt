package com.ramo.quran

import android.app.Application
import android.content.Context
import com.ramo.quran.helper.LocaleHelper
import com.yariksoffice.lingver.Lingver
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreference = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isFirstLogin = sharedPreference.getBoolean("isFirstLogin", true)
        if (isFirstLogin) {
            val localeId = LocaleHelper().getLocaleId(Locale.getDefault().language)
            //LocalSqliteHelper(this).changeLocaleWithResource(localeId)
            sharedPreference.edit().putBoolean("isFirstLogin", false).also { it.apply() }
        }
        Lingver.init(this)
    }
}