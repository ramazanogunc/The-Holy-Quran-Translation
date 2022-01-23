package com.ramo.quran.ext

import android.app.Activity
import com.irozon.sneaker.Sneaker
import com.ramo.quran.R


fun Activity.showSuccess(){
    Sneaker.with(this) // Activity, Fragment or ViewGroup
        .setTitle(getString(R.string.success))
        .setMessage(getString(R.string.success_message))
        .sneakSuccess()
}

fun Activity.showError(){
    Sneaker.with(this) // Activity, Fragment or ViewGroup
        .setTitle(getString(R.string.error))
        .setMessage(getString(R.string.error_message))
        .sneakError()
}