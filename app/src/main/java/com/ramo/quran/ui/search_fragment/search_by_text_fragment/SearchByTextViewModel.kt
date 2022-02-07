package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.model.VerseWithSurahName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchByTextViewModel @Inject constructor(
    private val verseRepository: VerseRepository
) : BaseViewModel() {


    private val _verses = MutableLiveData<List<VerseWithSurahName>>()
    val verses: LiveData<List<VerseWithSurahName>> = _verses

    fun search(text: String) {
        exec(
            showLoading = true,
            request = { verseRepository.searchVerse(text) },
            success = {
                _verses.value = it
            }
        )
    }

}