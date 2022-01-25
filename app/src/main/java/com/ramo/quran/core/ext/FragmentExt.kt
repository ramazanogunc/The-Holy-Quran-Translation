package com.ramo.quran.core.ext

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.safeContext(func: (Context) -> Unit) {
    context?.let(func)
}