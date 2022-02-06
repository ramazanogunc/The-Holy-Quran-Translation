package com.ramo.quran.ui.read_fragment

import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.core.ext.*
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.FragmentReadBinding
import com.ramo.quran.databinding.RecyclerReadItemBinding
import com.ramo.quran.model.db_translation.SurahName
import com.ramo.quran.model.db_translation.Verse
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.CommonDialogs
import com.ramo.quran.utils.SelectDialogModel
import com.ramo.quran.utils.getFontTypeFace
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReadFragment : BaseFragment<FragmentReadBinding, ReadViewModel>() {

    private var fontSize: Float? = null
    private val sweetRecyclerAdapter = SweetRecyclerAdapter<Verse>()

    @Inject
    lateinit var pref: AppSharedPref

    private var rvState: Parcelable? = null

    // Surah Indicator Views
    private var fabRight: FloatingActionButton? = null
    private var fabLeft: FloatingActionButton? = null
    private var versicleSize: TextView? = null
    private var surahNumber: TextView? = null


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
            rvState = layoutManager.onSaveInstanceState()
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity()?.showSurahIndicator()
        initSurahIndicator()
        binding.recyclerViewRead.layoutManager?.onRestoreInstanceState(rvState)
    }

    override fun onDestroyView() {
        mainActivity()?.hideSurahIndicator()
        super.onDestroyView()
    }

    private fun initUi() {
        fontSize = pref.fontSize
        sweetRecyclerAdapter.addHolder(R.layout.recycler_read_item, ::bindReadItem)

        withVB {
            recyclerViewRead.adapter = sweetRecyclerAdapter
        }
        viewModel.getData()
    }

    private fun initSurahIndicator() {
        fabRight = mainActivity()?.findViewById(R.id.fabRight)
        fabLeft = mainActivity()?.findViewById(R.id.fabLeft)
        surahNumber = mainActivity()?.findViewById(R.id.surahNumber)
        versicleSize = mainActivity()?.findViewById(R.id.versicleSize)

        fabRight?.setOnClickListener { viewModel.nextSurah() }
        fabLeft?.setOnClickListener { viewModel.previousSurah() }
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
                versicleSize?.text = getString(R.string.verse_number, surahVerses.size - 1)
            }
        }
        observe(viewModel.allSurahName) { allSurahName ->
            safeContext { context ->
                CommonDialogs.selectDialog(
                    context = context,
                    model = SelectDialogModel(
                        title = getString(R.string.change_surah),
                        description = getString(R.string.please_select_read_surah),
                        items = allSurahName.map { it.name }
                    ),
                    onSelect = { selectedSurah ->
                        viewModel.changeSurah(allSurahName[selectedSurah])
                    },
                    onDismiss = {
                        viewModel.deleteSurahs()
                    }
                )
            }
        }
    }

    private fun prepareSurahName(surahName: SurahName) {
        // there is two set title because. There is a bug when first run.
        (requireActivity() as MainActivity).supportActionBar?.title = surahName.name
        (requireActivity() as MainActivity).title = surahName.name
        surahNumber?.text = surahName.number.toString()
    }

    private fun bindReadItem(view: View, item: Verse) {
        val binding = RecyclerReadItemBinding.bind(view)

        with(binding) {
            try {
                val typeface = getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
                typeface?.let { itTypeface ->
                    verseNo.setTypeface(itTypeface, Typeface.BOLD)
                    verse.typeface = itTypeface
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }



            if (item.verseNo == 0) {
                divider.gone()
                verseNo.gone()
            } else {
                divider.visible()
                verseNo.visible()
            }

            verse.textSize = fontSize!!
            verseNo.textSize = fontSize!!

            verseNo.text = getString(R.string.versicle, item.verseNo)
            verse.text = item.verse
        }
    }

    private fun prepareFabButton(surahNo: Int) {
        withVB {
            fabLeft?.visible()
            fabRight?.visible()

            if (surahNo == 114)
                fabRight?.invisible()
            else if (surahNo == 1)
                fabLeft?.invisible()
        }
    }

    private fun mainActivity(): MainActivity? = activity as? MainActivity
}