package com.ramo.quran.ui.search_fragment.search_by_select_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.ConfigRepository
import com.ramo.quran.data.repository.SurahNameRepository
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.model.SurahWithVersicleNumbers
import com.ramo.quran.model.db_translation.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchBySelectViewModel @Inject constructor(
    private val surahNameRepository: SurahNameRepository,
    private val verseRepository: VerseRepository,
    private val configRepository: ConfigRepository
) : BaseViewModel() {

    var surahWithVersicleNumbers: List<SurahWithVersicleNumbers>? = null

    private val _selectedSurah = MutableLiveData<SurahWithVersicleNumbers>()
    val selectedSurah: LiveData<SurahWithVersicleNumbers> = _selectedSurah

    private val _selectedVerseNumber = MutableLiveData<Int>(0)
    val selectedVerseNumber: LiveData<Int> = _selectedVerseNumber

    private val _verse = MutableLiveData<Verse>()
    val verse: LiveData<Verse> = _verse


    init {
        getSurahWithVersicleNumbers()
    }

    fun surahNamesToString(list: List<SurahWithVersicleNumbers>): List<String> =
        list.map { "${it.surahName.number}. ${it.surahName.name}" }

    fun selectSurah(surah: SurahWithVersicleNumbers) {
        _selectedSurah.value = surah
        if (surah.versicleNumbers.last() < _selectedVerseNumber.value ?: 1)
            _selectedVerseNumber.value = 0
        getVerse()
    }

    fun selectVersicle(verseNumber: Int) {
        _selectedVerseNumber.value = verseNumber
        getVerse()
    }

    private fun getSurahWithVersicleNumbers() {
        exec(
            showLoading = true,
            request = { surahNameRepository.getAllSurahWithVersicleNumbers() },
            success = {
                surahWithVersicleNumbers = it
                _selectedSurah.value = it[0]
                getVerse()
            }
        )
    }

    private fun getVerse() {
        val surah = selectedSurah.value ?: return
        val verseNumber = selectedVerseNumber.value ?: return
        exec(
            showLoading = false,
            request = { verseRepository.getVerse(surah.surahName.number, verseNumber) },
            success = { _verse.value = it }
        )
    }
}