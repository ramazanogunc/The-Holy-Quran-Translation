package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.core.ext.gone
import com.ramo.quran.core.ext.visible
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.databinding.FragmentSearchByTextBinding
import com.ramo.quran.databinding.RecyclerSearchItemBinding
import com.ramo.quran.ext.textChangeDelayedListener
import com.ramo.quran.model.VerseWithSurahName
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchByTextFragment : BaseFragment<FragmentSearchByTextBinding, SearchByTextViewModel>() {

    private val sweetAdapter = SweetRecyclerAdapter<VerseWithSurahName>()
    private val pagingAdapter = SearchVersePagedAdapter()
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initAdapter()
        binding.editTextSearch.textChangeDelayedListener(1000) { text ->
            val flow = viewModel.search(text)
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                flow.collectLatest {
                    if (it == null) {
                        binding.textViewNoResult.visible()
                        binding.rvVerses.gone()

                    } else {
                        binding.rvVerses.visible()
                        binding.textViewNoResult.gone()
                        pagingAdapter.submitData(it)
                        //pagingAdapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        sweetAdapter.addHolder(R.layout.recycler_search_item) { v, item ->
            val binding = RecyclerSearchItemBinding.bind(v)
            with(binding) {
                verseNo.text = getString(R.string.verse_number, item.verseNo)
                surahName.text = getString(R.string.surah_name, item.surahName)
                verse.text = item.verse
            }
        }
        binding.rvVerses.adapter = pagingAdapter
    }
}


class SearchPagingSource(private val verseRepository: VerseRepository, private val query: String) :
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