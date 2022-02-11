package com.ramo.quran.ui.search_fragment.search_by_select_fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.ramo.core.VbAndVmFragment
import com.ramo.core.ext.observe
import com.ramo.quran.R
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.FragmentSearchBySelectBinding
import com.ramo.quran.ui.search_fragment.SearchFragment
import com.ramo.quran.utils.CommonDialogs
import com.ramo.quran.utils.SelectDialogModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchBySelectFragment :
    VbAndVmFragment<FragmentSearchBySelectBinding, SearchBySelectViewModel>() {

    @Inject
    lateinit var pref: AppSharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initObserver() {
        observe(viewModel.selectedSurah) {
            binding.textViewSurahName.text = it.surahName.name
        }
        observe(viewModel.selectedVerseNumber) {
            binding.textViewVerseNumber.text = it.toString()
        }
        observe(viewModel.verse) {
            binding.textViewVerse.text = it.verse
        }
    }

    private fun initView() {
        withVB {
            btnSurahSelect.setOnClickListener { onClickSurahSelect() }
            btnVerseSelect.setOnClickListener { onClickVerseSelect() }
            btnJumpReadPage.setOnClickListener { onClickJumpToReadPage() }
        }
    }

    private fun onClickJumpToReadPage() {
        val selectedSurah = viewModel.selectedSurah.value ?: return
        pref.readPosition = viewModel.selectedVerseNumber.value ?: 0
        pref.currentSurah = selectedSurah.surahName.number
        (parentFragment as? SearchFragment)?.findNavController()
            ?.navigate(R.id.action_search_to_read)
    }

    private fun onClickSurahSelect() {
        val safeContext = context ?: return
        val data = viewModel.surahWithVersicleNumbers ?: return
        CommonDialogs.selectDialog(
            context = safeContext,
            model = SelectDialogModel(
                title = getString(R.string.select_surah),
                items = viewModel.surahNamesToString(data)
            ),
            onSelect = { position ->
                viewModel.selectSurah(data[position])
            }
        )
    }

    private fun onClickVerseSelect() {
        val safeContext = context ?: return
        val selectedSurah = viewModel.selectedSurah.value ?: return
        CommonDialogs.selectDialog(
            context = safeContext,
            model = SelectDialogModel(
                title = getString(R.string.select_verse),
                items = selectedSurah.versicleNumbers.map { it.toString() }
            ),
            onSelect = { position ->
                viewModel.selectVersicle(selectedSurah.versicleNumbers[position])
            }
        )
    }
}