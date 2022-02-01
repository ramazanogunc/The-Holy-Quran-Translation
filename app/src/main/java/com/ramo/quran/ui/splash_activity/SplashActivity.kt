package com.ramo.quran.ui.splash_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var pref: AppSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.detectLanguageInFirstOpen()
        ThemeHelper.setThemeMode(pref.appTheme)

        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}