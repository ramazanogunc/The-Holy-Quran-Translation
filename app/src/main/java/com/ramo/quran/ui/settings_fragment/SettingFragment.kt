package com.ramo.quran.ui.settings_fragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.core.ext.observe
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.FragmentSettingsBinding
import com.ramo.quran.ext.showSuccess
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.CommonDialogs
import com.ramo.quran.utils.SelectDialogModel
import com.ramo.quran.utils.getFontTypeFace
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    @Inject
    lateinit var pref: AppSharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObserver()
        viewModel.getAllLanguage()
        viewModel.getCurrentLanguage()
    }

    private fun initUi() {
        (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.settings)

        withVB {
            btnFontFamily.setOnClickListener { onFontFamilyClick(it) }
            btnFontSize.setOnClickListener { onFontSizeClick(it) }
            btnKeepScreenOn.setOnClickListener { onKeepScreenOnClick(it) }
            btnLanguage.setOnClickListener { onLanguageClick(it) }
        }

        initSavedConfig()
    }

    private fun initObserver() {
        observe(viewModel.currentLanguage) {
            binding.btnLanguage.text = it.name
        }
    }

    private fun initSavedConfig() {
        setFontInfo()
        setKeepOnScreen()
    }

    private fun setKeepOnScreen() {
        val isKeepOnScreen = pref.isKeepScreenOn
        binding.btnKeepScreenOn.text = getText(if (isKeepOnScreen) R.string.yes else R.string.no)
    }

    private fun onFontFamilyClick(v: View) {
        val fontFields = R.font::class.java.fields
        val fontNames = fontFields.map { it.name }
        val context = context ?: return
        CommonDialogs.selectDialog(
            context = context,
            model = SelectDialogModel(
                title = "Font Seç",
                description = "Okumak istediğiniz fontu seçiniz",
                items = fontNames
            ),
            onSelect = { position ->
                val selectedFont = fontFields[position]
                pref.changeSelectedFont(selectedFont.name, selectedFont.getInt(selectedFont))
                setFontInfo()
            }
        )
    }

    private fun onFontSizeClick(v: View) {
        val menu = PopupMenu(requireContext(), v)
        viewModel.fontSizeArray.forEach { menu.menu.add(it) }
        menu.setOnMenuItemClickListener {
            val fontSize = it.title.toString().toFloat()
            pref.fontSize = fontSize
            requireActivity().showSuccess()
            setFontInfo()
            return@setOnMenuItemClickListener true
        }
        menu.show()
    }

    private fun onKeepScreenOnClick(v: View) {
        val menu = PopupMenu(requireContext(), v)
        val yesNoValues = arrayOf(getString(R.string.yes), getString(R.string.no))
        yesNoValues.forEach { menu.menu.add(it) }
        menu.setOnMenuItemClickListener { menuItem ->
            val selectedIsKeepScreenOn = menuItem.title == getString(R.string.yes)
            pref.isKeepScreenOn = selectedIsKeepScreenOn
            requireActivity().recreate()
            return@setOnMenuItemClickListener true
        }
        menu.show()
    }

    private fun onLanguageClick(v: View) {
        viewModel.languageList?.let { itLanguageList ->
            val menu = PopupMenu(requireContext(), v)
            itLanguageList.forEach { menu.menu.add(it.name) }
            menu.setOnMenuItemClickListener { menuItem ->
                val selectedLang = itLanguageList.first { it.name == menuItem.title }
                viewModel.changeLanguage(selectedLang) {
                    Lingver.getInstance()
                        .setLocale(requireContext(), selectedLang.key)
                    requireActivity().recreate()
                }
                return@setOnMenuItemClickListener true
            }
            menu.show()
        }
    }

    private fun setFontInfo() {
        val fontSize = pref.fontSize
        withVB {
            btnFontSize.text = fontSize.toString()
            textViewExample.textSize = fontSize
            btnFontFamily.text = pref.getCurrentFontName()
            textViewExample.typeface =
                getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
        }
    }

}