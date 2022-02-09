package com.ramo.quran.ui.search_fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.ramo.quran.R
import com.ramo.quran.core.SimpleBaseFragment
import com.ramo.quran.databinding.FragmentSearchBinding
import com.ramo.quran.ui.search_fragment.adapter.SearchFragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : SimpleBaseFragment<FragmentSearchBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = SearchFragmentStateAdapter(
            listOf(
                getString(R.string.search_by_text),
                getString(R.string.search_by_select)
            ), this
        )
        withVB {
            pager.adapter = adapter
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = adapter.getTitle(position)
            }.attach()
        }
    }
}