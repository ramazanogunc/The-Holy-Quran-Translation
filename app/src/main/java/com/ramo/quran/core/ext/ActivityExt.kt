package com.ramo.quran.core.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, { it?.let { t -> observer(t) } })
}

fun <T> AppCompatActivity.observe(liveData: MutableLiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, { it?.let { t -> observer(t) } })
}