package com.ramo.quran.ui.fragment.read_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.databinding.FragmentReadBinding
import com.ramo.quran.ext.*
import com.ramo.quran.model.SurahName
import com.ramo.quran.model.Verse
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.getFontTypeFace
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReadFragment : BaseFragment<FragmentReadBinding, ReadViewModel>() {

    private lateinit var surahDialog: AlertDialog
    private val fontSize by lazy { pref.getFontSize() }
    private val sweetRecyclerAdapter = SweetRecyclerAdapter<Verse>()

    @Inject
    lateinit var pref: AppSharedPref

    private val appDatabase by lazy { AppDatabase.getDatabase(requireContext()) }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.read_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeSurah -> initSurahDialog()
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initUi()
    }

    override fun onPause() {
        super.onPause()
        withVB {
            val position =
                (recyclerViewRead.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            pref.setReadPosition(position)
        }
    }

    private fun initUi() {
        sweetRecyclerAdapter.addHolder(R.layout.recycler_read_item, ::bindReadItem)

        withVB {
            recyclerViewRead.adapter = sweetRecyclerAdapter

            // fab on clicks
            fabRight.setOnClickListener { onRightFabClick() }
            fabLeft.setOnClickListener { onLeftFabClick() }
        }
        prepareData()
    }

    private fun bindReadItem(view: View, item: Verse) {
        val txtVerseNo = view.findViewById<TextView>(R.id.verseNo)
        val txtVerse = view.findViewById<TextView>(R.id.verse)

        txtVerseNo.typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
        txtVerse.typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())

        if (item.verseNo == 0) txtVerseNo.hide()
        else txtVerseNo.visible()

        txtVerse.textSize = fontSize
        txtVerseNo.textSize = fontSize

        txtVerseNo.text = item.verseNo.toString() + getString(R.string.versicle)
        txtVerse.text = item.verse
    }

    private fun prepareData() {
        val currentSurahNumber = pref.getCurrentSurah()
        val surahName = appDatabase.surahNameDao.getCurrentSurahName(currentSurahNumber)
        // val surahVerses = appDatabase.verseDao.getCurrentSurahVerses(currentSurahNumber)
        viewModel.getVerses(currentSurahNumber)
        observe(viewModel.verses) { surahVerses ->
            withVB {
                (recyclerViewRead.adapter as SweetRecyclerAdapter<Verse>).submitList(surahVerses)
                prepareSurahName(surahName, surahVerses.size)
                prepareFabButton(surahName.number)
                recyclerViewRead.scrollToPosition(pref.getReadPosition())
            }
        }
    }

    private fun initSurahDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.choice_surah))
        val surahArray = appDatabase.surahNameDao.getAllSurahName()
        val stringArray: List<String> = surahArray.map { it.name }
        builder.setItems(
            stringArray.toTypedArray()
        ) { _, which ->
            changeSurah(surahArray[which])
        }
        surahDialog = builder.create()
        surahDialog.show()
    }

    private fun changeSurah(nameOfSurah: SurahName) {
        pref.changeCurrentSurah(nameOfSurah.number)
        prepareData()
    }

    private fun onLeftFabClick() {
        pref.setReadPosition(0)
        pref.changeCurrentSurah(pref.getCurrentSurah() - 1)
        prepareData()
    }

    private fun onRightFabClick() {
        pref.setReadPosition(0)
        pref.changeCurrentSurah(pref.getCurrentSurah() + 1)
        prepareData()
    }

    private fun prepareFabButton(surahNo: Int) {
        withVB {
            fabLeft.visible()
            bottomCenter.visible()
            fabRight.visible()

            // check first or last surah
            if (surahNo == 114)
                fabRight.invisible()
            else if (surahNo == 1)
                fabLeft.invisible()
        }
    }

    private fun prepareSurahName(surahName: SurahName, versesSize: Int) {
        // there is two set title because. There is a bug when first run.
        (requireActivity() as MainActivity).supportActionBar?.title = surahName.name
        (requireActivity() as MainActivity).title = surahName.name
        withVB {
            surahNumber.text = surahName.number.toString()
            versicleSize.text = getString(R.string.verse_number) + versesSize.toString()
        }
    }
}