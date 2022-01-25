package com.ramo.quran.ui.read_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.model.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val verseRepository: VerseRepository
) : BaseViewModel() {

    private val _verses = MutableLiveData<List<Verse>>()
    val verses: LiveData<List<Verse>> = _verses

    fun getVerses(surahNumber: Int) {
        exec(
            showLoading = true,
            request = { verseRepository.getCurrentSurahVerses(surahNumber) },
            success = { _verses.value = it }
        )
    }
}