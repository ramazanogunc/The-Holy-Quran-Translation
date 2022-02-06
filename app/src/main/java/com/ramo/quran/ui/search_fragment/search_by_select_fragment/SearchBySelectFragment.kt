package com.ramo.quran.ui.search_fragment.search_by_select_fragment

import android.os.Bundle
import android.view.View
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.databinding.FragmentSearchBySelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBySelectFragment :
    BaseFragment<FragmentSearchBySelectBinding, SearchBySelectViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }
}