package com.ramo.quran.ui.settings_fragment

import android.os.Bundle
import android.view.View
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.core.ext.observe
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.FragmentSettingsBinding
import com.ramo.quran.model.AppTheme
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.CommonDialogs
import com.ramo.quran.utils.SelectDialogModel
import com.ramo.quran.utils.ThemeHelper
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
            menuFont.setOnClickListener { onFontFamilyClick() }
            menuFontSize.setOnClickListener { onFontSizeClick() }
            menuLanguage.setOnClickListener { onLanguageClick() }
            menuTheme.setOnClickListener { onThemeClick() }
            switchKeepScreen.setOnCheckedChangeListener { _, isChecked ->
                pref.isKeepScreenOn = isChecked
            }
        }

        initSavedConfig()
    }

    private fun initObserver() {
        observe(viewModel.currentLanguage) {
            binding.languageDescription.text = it.name
        }
    }

    private fun initSavedConfig() {
        setFontInfo()
        binding.switchKeepScreen.isChecked = pref.isKeepScreenOn
        setAppThemeString()
    }

    private fun onFontFamilyClick() {
        val fontFields = R.font::class.java.fields
        val fontNames = fontFields.map { it.name }
        val context = context ?: return
        CommonDialogs.selectDialog(
            context = context,
            model = SelectDialogModel(
                title = getString(R.string.font_famiy),
                description = getString(R.string.please_select_font),
                items = fontNames
            ),
            onSelect = { position ->
                val selectedFont = fontFields[position]
                pref.changeSelectedFont(selectedFont.name, selectedFont.getInt(selectedFont))
                setFontInfo()
            }
        )
    }

    private fun onFontSizeClick() {
        val context = context ?: return
        CommonDialogs.selectDialog(
            context = context,
            model = SelectDialogModel(
                title = getString(R.string.font_size),
                description = getString(R.string.please_select_font_size),
                items = viewModel.fontSizeArray.toList()
            ),
            onSelect = { position ->
                val selectedFontSize = viewModel.fontSizeArray[position]
                pref.fontSize = selectedFontSize.toFloat()
                setFontInfo()
            }
        )
    }

    private fun onLanguageClick() {
        val context = context ?: return
        viewModel.languageList?.let { itLanguageList ->
            CommonDialogs.selectDialog(
                context = context,
                model = SelectDialogModel(
                    title = getString(R.string.app_language),
                    description = getString(R.string.please_select_language),
                    items = itLanguageList.map { it.name }
                ),
                onSelect = { position ->
                    val selectedLang = itLanguageList[position]
                    viewModel.changeLanguage(selectedLang) {
                        Lingver.getInstance()
                            .setLocale(requireContext(), selectedLang.key)
                        requireActivity().recreate()
                    }
                })
        }
    }

    private fun onThemeClick() {
        val context = context ?: return
        val themes = listOf<String>(
            getString(R.string.system_default),
            getString(R.string.dark),
            getString(R.string.light)
        )
        CommonDialogs.selectDialog(
            context = context,
            model = SelectDialogModel(
                title = getString(R.string.theme),
                description = getString(R.string.please_select_app_theme),
                items = themes
            ),
            onSelect = { position ->
                val appTheme: AppTheme = when (position) {
                    0 -> AppTheme.SYSTEM_DEFAULT
                    1 -> AppTheme.DARK
                    2 -> AppTheme.LIGHT
                    else -> AppTheme.SYSTEM_DEFAULT
                }
                pref.appTheme = appTheme
                setAppThemeString()
                ThemeHelper.setThemeMode(appTheme)
            })


    }

    private fun setAppThemeString() {
        binding.themeDescription.text = getString(
            when (pref.appTheme) {
                AppTheme.SYSTEM_DEFAULT -> R.string.system_default
                AppTheme.DARK -> R.string.dark
                AppTheme.LIGHT -> R.string.light
            }
        )
    }

    private fun setFontInfo() {
        val fontSize = pref.fontSize
        withVB {
            fontSizeDescription.text = fontSize.toString()
            textViewExample.textSize = fontSize
            fontDescription.text = pref.getCurrentFontName()
            try {
                textViewExample.typeface =
                    getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }

}