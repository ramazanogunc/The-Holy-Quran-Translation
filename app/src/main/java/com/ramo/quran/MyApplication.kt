package com.ramo.quran

import android.app.Application
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.helper.AppSharedPref
import com.ramo.quran.helper.LocaleHelper
import com.yariksoffice.lingver.Lingver
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val pref = AppSharedPref(this)
        if (pref.isFirstLogin()) {
            val localeId = LocaleHelper().getLocaleId(Locale.getDefault().language)
            AppDatabase.getDatabase(this).configDao.changeLocaleWithResource(localeId)
            pref.setIsFirstLogin(false)
        }
        Lingver.init(this)
    }
}