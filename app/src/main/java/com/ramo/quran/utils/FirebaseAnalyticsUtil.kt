package com.ramo.quran.utils

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.ui.change_resource_fragment.ChangeResourceFragment
import com.ramo.quran.ui.read_fragment.ReadFragment
import com.ramo.quran.ui.search_fragment.SearchFragment
import com.ramo.quran.ui.search_fragment.search_by_select_fragment.SearchBySelectFragment
import com.ramo.quran.ui.search_fragment.search_by_text_fragment.SearchByTextFragment
import com.ramo.quran.ui.settings_fragment.SettingFragment
import com.ramo.quran.ui.splash_activity.SplashActivity
import com.ramo.quran.ui.terms_of_use_fragment.TermsOfUseFragment

object FirebaseAnalyticsUtil {
    private var frAnalytics: FirebaseAnalytics? = null

    fun setAnalytics(context: Context) {
        frAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun screenEvent(className: Class<*>) {
        Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, getScreenName(className))
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, className.simpleName)
        }.run {
            frAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, this)
        }

    }

    fun getScreenName(className: Class<*>): String = when (className) {
        SplashActivity::class.java -> "Splash Screen"
        MainActivity::class.java -> "Main Screen"
        ReadFragment::class.java -> "Read Screen"
        SearchFragment::class.java -> "Search Screen"
        SearchBySelectFragment::class.java -> "Search by Select Screen"
        SearchByTextFragment::class.java -> "Search by Text Screen"
        ChangeResourceFragment::class.java -> "Change Resource Screen"
        SettingFragment::class.java -> "Settings Screen"
        TermsOfUseFragment::class.java -> "Terms Of Use Screen"
        else -> className.simpleName
    }
}