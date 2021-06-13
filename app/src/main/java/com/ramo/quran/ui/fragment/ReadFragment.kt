package com.ramo.quran.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.ramo.quran.R
import com.ramo.quran.helper.*
import com.ramo.quran.model.SurahName
import com.ramo.quran.model.Verse
import com.ramo.quran.ui.MainActivity
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : HasDatabaseFragment() {

    private lateinit var surahDialog: AlertDialog
    private val pref by lazy { AppSharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.read_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeSurah -> surahDialog.show()
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initUi()
    }

    private fun initUi() {
        val fontSize = pref.getFontSize()
        // init recycler view adapter
        val sweetRecyclerAdapter = SweetRecyclerAdapter<Verse>()
        sweetRecyclerAdapter.addHolder(R.layout.recycler_read_item) { view, item ->
            val txtVerseNo = view.findViewById<TextView>(R.id.verseNo)
            val txtVerse = view.findViewById<TextView>(R.id.verse)

            txtVerseNo.typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
            txtVerse.typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())

            if (item.verseNo == 0) txtVerseNo.hide()

            txtVerse.textSize = fontSize
            txtVerseNo.textSize = fontSize

            txtVerseNo.text = item.verseNo.toString() + getString(R.string.versicle)
            txtVerse.text = item.verse
        }
        recyclerViewRead.adapter = sweetRecyclerAdapter

        prepareData()

        // fab on clicks
        fabRight.setOnClickListener { onRightFabClick() }
        fabLeft.setOnClickListener { onLeftFabClick() }

        initSurahDialog()
    }

    private fun prepareData() {
        val currentSurahNumber = pref.getCurrentSurah()
        val surahName = appDatabase.surahNameDao.getCurrentSurahName(currentSurahNumber)
        val surahVerses = appDatabase.verseDao.getCurrentSurahVerses(currentSurahNumber)

        (recyclerViewRead.adapter as SweetRecyclerAdapter<Verse>).submitList(surahVerses)
        prepareSurahName(surahName, surahVerses.size)
        prepareFabButton(surahName.number)
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
    }

    private fun changeSurah(nameOfSurah: SurahName) {
        pref.changeCurrentSurah(nameOfSurah.number)
        prepareData()
    }

    private fun onLeftFabClick() {
        pref.changeCurrentSurah(pref.getCurrentSurah() - 1)
        prepareData()
    }

    private fun onRightFabClick() {
        pref.changeCurrentSurah(pref.getCurrentSurah() + 1)
        prepareData()
    }

    private fun prepareFabButton(surahNo: Int) {
        bottom_left.show()
        bottom_center.show()
        bottom_right.show()

        // check first or last surah
        if (surahNo == 114)
            bottom_right.invisible()
        else if (surahNo == 1)
            bottom_left.invisible()

    }

    private fun prepareSurahName(surahName: SurahName, versesSize: Int) {
        (activity as MainActivity).supportActionBar?.title = surahName.name
        surahNumber.text = surahName.number.toString()
        versicleSize.text = getString(R.string.verse_number) + versesSize.toString()
        previousSurahName.text = "aa"
        nextSurahName.text = "bbb"
    }
}