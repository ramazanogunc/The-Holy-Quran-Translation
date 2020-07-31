package com.ramo.quran.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.ramo.quran.model.Language
import java.util.*


fun isLangLoaded(mContext: Context):Boolean{
    val status = mContext.getSharedPreferences("lang",Context.MODE_PRIVATE).getBoolean("is_loaded",false)
    return status
}

fun setLangIsLoaded(mContext: Context, b: Boolean) {
    val edit = mContext.getSharedPreferences("lang",Context.MODE_PRIVATE).edit()
    edit.putBoolean("is_loaded",b)
    edit.commit()
}

fun Activity.setLocale(context: Context, language: String) {

    val locale= Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        config.setLocale(locale)
    }else {
        config.locale = locale
    }

    this.baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
}



