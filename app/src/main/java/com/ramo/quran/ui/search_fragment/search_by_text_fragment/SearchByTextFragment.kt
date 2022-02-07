package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import android.os.Bundle
import android.view.View
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.core.ext.gone
import com.ramo.quran.core.ext.observe
import com.ramo.quran.core.ext.visible
import com.ramo.quran.databinding.FragmentSearchByTextBinding
import com.ramo.quran.databinding.RecyclerSearchItemBinding
import com.ramo.quran.ext.textChangeDelayedListener
import com.ramo.quran.model.VerseWithSurahName
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchByTextFragment : BaseFragment<FragmentSearchByTextBinding, SearchByTextViewModel>() {

    private val sweetAdapter = SweetRecyclerAdapter<VerseWithSurahName>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        initAdapter()
        binding.editTextSearch.textChangeDelayedListener(1000) { text ->
            if (text.length > 4)
                viewModel.search(text)
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
        binding.rvVerses.adapter = sweetAdapter
    }

    private fun initObserver() {
        observe(viewModel.verses) {
            if (it.isEmpty()) {
                binding.textViewNoResult.visible()
                binding.rvVerses.gone()

            } else {
                binding.rvVerses.visible()
                binding.textViewNoResult.gone()
                sweetAdapter.submitList(it)
            }

        }
    }
}