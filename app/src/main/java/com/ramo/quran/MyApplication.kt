package com.ramo.quran

import android.app.Application
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.utils.LocaleHelper
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val pref = AppSharedPref(this)
        if (pref.isFirstLogin) {
            val localeId = LocaleHelper().getLocaleId(Locale.getDefault().language)
            GlobalScope.launch {
                AppDatabase.getDatabase(this@MyApplication).configDao.changeLocaleWithResource(
                    localeId
                )
            }
            pref.isFirstLogin = false
        }
        Lingver.init(this)
    }
}