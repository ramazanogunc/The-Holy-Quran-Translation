package com.ramo.quran.ui.splash_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramo.quran.data.repository.ConfigRepository
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.utils.LocaleHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val pref: AppSharedPref,
    private val configRepository: ConfigRepository
) : ViewModel() {

    fun detectLanguageInFirstOpen(onComplete: () -> Unit) {
        viewModelScope.launch {
            if (pref.isFirstLogin) {
                val localeId = LocaleHelper.getLocaleId(Locale.getDefault().language)
                configRepository.changeLocaleWithResource(localeId)
                pref.isFirstLogin = false
                onComplete.invoke()
            } else
                onComplete.invoke()
        }
    }
}