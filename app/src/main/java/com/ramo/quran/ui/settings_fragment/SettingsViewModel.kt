package com.ramo.quran.ui.settings_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.ConfigRepository
import com.ramo.quran.data.repository.LanguageRepository
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.model.db_translation.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val languageRepository: LanguageRepository,
    private val configRepository: ConfigRepository,
    private val pref: AppSharedPref
) : BaseViewModel() {

    val fontSizeArray = arrayOf("14", "16", "18", "20", "22", "24", "26")

    var languageList: List<Language>? = null

    private val _currentLanguage = MutableLiveData<Language>()
    val currentLanguage: LiveData<Language> = _currentLanguage

    fun getAllLanguage() {
        exec(
            request = { languageRepository.getAllLanguages() },
            success = { languageList = it }
        )
    }

    fun getCurrentLanguage() {
        exec(
            request = { languageRepository.getCurrentLanguage() },
            success = { _currentLanguage.value = it }
        )
    }

    fun changeLanguage(selectedLang: Language, function: () -> Unit) {
        exec(
            request = { configRepository.changeLocaleWithResource(selectedLang.id!!) },
            success = { function.invoke() }
        )
    }
}