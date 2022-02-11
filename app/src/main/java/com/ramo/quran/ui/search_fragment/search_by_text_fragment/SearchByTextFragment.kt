package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ramo.core.VbAndVmFragment
import com.ramo.core.ext.gone
import com.ramo.core.ext.visible
import com.ramo.quran.R
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.FragmentSearchByTextBinding
import com.ramo.quran.ext.textChangeDelayedListener
import com.ramo.quran.model.VerseWithSurahName
import com.ramo.quran.ui.search_fragment.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchByTextFragment : VbAndVmFragment<FragmentSearchByTextBinding, SearchByTextViewModel>() {

    @Inject
    lateinit var pref: AppSharedPref
    private val pagingAdapter = SearchVersePagedAdapter(::onItemClick)
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initAdapter()
        binding.editTextSearch.textChangeDelayedListener(1000) { text ->
            if (text.isBlank()) return@textChangeDelayedListener
            val flow = viewModel.search(text)
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                flow.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rvVerses.adapter = pagingAdapter
        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collect {
                if (it.append is LoadState.NotLoading &&
                    it.append.endOfPaginationReached &&
                    pagingAdapter.itemCount < 1
                ) {
                    binding.textViewNoResult.visible()
                    binding.rvVerses.gone()
                } else {
                    binding.rvVerses.visible()
                    binding.textViewNoResult.gone()

                }
            }
        }
    }

    private fun onItemClick(verseWithSurahName: VerseWithSurahName) {
        pref.readPosition = verseWithSurahName.verseNo
        pref.currentSurah = verseWithSurahName.surahNumber
        (parentFragment as? SearchFragment)?.findNavController()
            ?.navigate(R.id.action_search_to_read)
    }
}


class SearchPagingSource(
    private val verseRepository: VerseRepository,
    private val query: String
) :
    PagingSource<Int, VerseWithSurahName>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VerseWithSurahName> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val randomPosts = verseRepository.searchVerse(query, position, params.loadSize)
        return LoadResult.Page(
            data = randomPosts,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (randomPosts.isNullOrEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, VerseWithSurahName>): Int? = null
}