package com.ramo.quran.ui.read_fragment

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.SurahNameRepository
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.model.SurahName
import com.ramo.quran.model.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val verseRepository: VerseRepository,
    private val surahNameRepository: SurahNameRepository,
    private val pref: AppSharedPref
) : BaseViewModel() {

    private val _verses = MutableLiveData<List<Verse>>()
    val verses: LiveData<List<Verse>> = _verses

    private val _surahName = MutableLiveData<SurahName>()
    val surahName: LiveData<SurahName> = _surahName

    private val _allSurahName = MutableLiveData<List<SurahName>>()
    val allSurahName: LiveData<List<SurahName>> = _allSurahName

    fun getData() {
        getCurrentVerses()
        getCurrentSurahName()
    }

    private fun getCurrentVerses() {
        exec(
            showLoading = true,
            request = { verseRepository.getCurrentSurahVerses() },
            success = { _verses.value = it }
        )
    }

    private fun getCurrentSurahName() {
        exec(
            showLoading = false,
            request = { surahNameRepository.getCurrentSurahName() },
            success = { _surahName.value = it }
        )
    }

    fun getAllSurahForSelect() {
        exec(
            showLoading = true,
            request = { surahNameRepository.getAllSurahName() },
            success = { _allSurahName.value = it }
        )
    }

    fun changeSurah(surahName: SurahName) {
        pref.readPosition = 0
        pref.currentSurah = surahName.number
        getData()
        deleteSurahs()
    }


    @SuppressLint("NullSafeMutableLiveData")
    fun deleteSurahs() {
        _allSurahName.value = null
    }

    fun previousSurah() {
        pref.readPosition = 0
        pref.currentSurah = pref.currentSurah - 1
        getData()
    }

    fun nextSurah() {
        pref.readPosition = 0
        pref.currentSurah = pref.currentSurah + 1
        getData()
    }
}