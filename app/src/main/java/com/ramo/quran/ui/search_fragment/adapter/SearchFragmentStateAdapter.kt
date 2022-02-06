package com.ramo.quran.ui.search_fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ramo.quran.ui.search_fragment.search_by_select_fragment.SearchBySelectFragment

class SearchFragmentStateAdapter(private val titles: List<String>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SearchBySelectFragment()
        1 -> SearchBySelectFragment()
        else -> SearchBySelectFragment()
    }

    fun getTitle(position: Int): String = titles[position]

}