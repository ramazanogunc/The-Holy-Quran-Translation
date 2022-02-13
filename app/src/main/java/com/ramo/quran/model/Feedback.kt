package com.ramo.quran.model

import android.os.Build
import com.google.firebase.database.IgnoreExtraProperties
import com.ramo.quran.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
data class Feedback(
    val description: String,
    val appVersion: String = BuildConfig.VERSION_NAME,
    val androidVersion: String = Build.VERSION.RELEASE,
    val brand: String = Build.BRAND,
    val device: String = Build.DEVICE,
    val model: String = Build.MODEL,
    val time: String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date()),
)
