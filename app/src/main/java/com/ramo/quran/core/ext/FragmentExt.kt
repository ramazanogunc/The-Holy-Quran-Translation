package com.ramo.quran.core.ext

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun Fragment.safeContext(func: (Context) -> Unit) {
    context?.let(func)
}

fun <T> Fragment.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { it?.let { t -> observer(t) } }
}

fun <T> Fragment.observe(liveData: MutableLiveData<T>, observer: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { it?.let { t -> observer(t) } }
}