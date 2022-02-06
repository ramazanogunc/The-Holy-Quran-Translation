package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import android.os.Bundle
import android.view.View
import com.ramo.quran.core.SimpleBaseFragment
import com.ramo.quran.databinding.FragmentSearchByTextBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchByTextFragment : SimpleBaseFragment<FragmentSearchByTextBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

    }
}