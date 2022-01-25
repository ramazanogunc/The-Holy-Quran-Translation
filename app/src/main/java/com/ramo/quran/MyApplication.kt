package com.ramo.quran

import android.app.Application
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.utils.LocaleHelper
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val pref = AppSharedPref(this)
        if (pref.isFirstLogin) {
            val localeId = LocaleHelper().getLocaleId(Locale.getDefault().language)
            AppDatabase.getDatabase(this).configDao.changeLocaleWithResource(localeId)
            pref.isFirstLogin = false
        }
        Lingver.init(this)
    }
}