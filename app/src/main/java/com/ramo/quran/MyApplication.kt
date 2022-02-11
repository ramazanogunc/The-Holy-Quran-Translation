package com.ramo.quran

import android.app.Application
import com.ramo.quran.utils.FirebaseAnalyticsUtil
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this)
        FirebaseAnalyticsUtil.setAnalytics(this)
    }
}