package com.ramo.quran.ui.read_fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.core.ext.gone
import com.ramo.quran.core.ext.invisible
import com.ramo.quran.core.ext.safeContext
import com.ramo.quran.core.ext.visible
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.FragmentReadBinding
import com.ramo.quran.databinding.RecyclerReadItemBinding
import com.ramo.quran.ext.observe
import com.ramo.quran.model.SurahName
import com.ramo.quran.model.Verse
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.CustomDialogs
import com.ramo.quran.utils.getFontTypeFace
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReadFragment : BaseFragment<FragmentReadBinding, ReadViewModel>() {

    private val fontSize by lazy { pref.fontSize }
    private val sweetRecyclerAdapter = SweetRecyclerAdapter<Verse>()

    @Inject
    lateinit var pref: AppSharedPref


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.read_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeSurah -> viewModel.getAllSurahForSelect()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initUi()
        initObserver()
    }

    override fun onPause() {
        super.onPause()
        withVB {
            val layoutManager = (recyclerViewRead.layoutManager as LinearLayoutManager)
            val position = layoutManager.findFirstVisibleItemPosition()
            pref.readPosition = position
        }
    }

    private fun initUi() {
        sweetRecyclerAdapter.addHolder(R.layout.recycler_read_item, ::bindReadItem)

        withVB {
            recyclerViewRead.adapter = sweetRecyclerAdapter

            // fab on clicks
            fabRight.setOnClickListener { viewModel.nextSurah() }
            fabLeft.setOnClickListener { viewModel.previousSurah() }
        }
        viewModel.getData()
    }

    private fun initObserver() {
        observe(viewModel.surahName) { surahName ->
            prepareFabButton(surahName.number)
            prepareSurahName(surahName)
        }
        observe(viewModel.verses) { surahVerses ->
            withVB {
                sweetRecyclerAdapter.submitList(surahVerses)
                recyclerViewRead.scrollToPosition(pref.readPosition)
                versicleSize.text = getString(R.string.verse_number, surahVerses.size)
            }
        }
        observe(viewModel.allSurahName) { allSurahName ->
            val stringAllSurahName: List<String> = allSurahName.map { it.name }
            safeContext {
                CustomDialogs.changeSurah(it, stringAllSurahName) { selectedPosition ->
                    viewModel.changeSurah(allSurahName[selectedPosition])
                }
            }
        }
    }

    private fun prepareSurahName(surahName: SurahName) {
        // there is two set title because. There is a bug when first run.
        (requireActivity() as MainActivity).supportActionBar?.title = surahName.name
        (requireActivity() as MainActivity).title = surahName.name
        binding.surahNumber.text = surahName.number.toString()
    }

    private fun bindReadItem(view: View, item: Verse) {
        val binding = RecyclerReadItemBinding.bind(view)

        with(binding) {
            verseNo.typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
            verse.typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())

            if (item.verseNo == 0) verseNo.gone()
            else verseNo.visible()

            verse.textSize = fontSize
            verseNo.textSize = fontSize

            verseNo.text = getString(R.string.versicle, item.verseNo)
            verse.text = item.verse
        }
    }

    private fun prepareFabButton(surahNo: Int) {
        withVB {
            fabLeft.visible()
            fabRight.visible()

            if (surahNo == 114)
                fabRight.invisible()
            else if (surahNo == 1)
                fabLeft.invisible()
        }
    }
}