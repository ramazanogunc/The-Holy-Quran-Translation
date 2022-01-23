package com.ramo.quran.ui.fragment.read_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.data.dao.VerseDao
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.model.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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