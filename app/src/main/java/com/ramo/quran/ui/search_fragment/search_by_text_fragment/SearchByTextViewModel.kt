package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ramo.quran.core.BaseViewModel
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.model.VerseWithSurahName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchByTextViewModel @Inject constructor(
    private val verseRepository: VerseRepository
) : BaseViewModel() {

    fun search(text: String): Flow<PagingData<VerseWithSurahName>> {
        return Pager(PagingConfig(10)) {
            //SearchPagingSource(verseRepository, text)
            verseRepository.searchVerse(text)
        }.flow
    }

}