package com.ramo.quran.utils

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

fun getFontTypeFace(context: Context, fontResourceId: Int): Typeface? {
    return ResourcesCompat.getFont(context, fontResourceId)
}