package com.ramo.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.ramo.quran.R
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.ext.showSuccess
import com.ramo.quran.model.Language
import com.ramo.quran.ui.MainActivity
import com.ramo.quran.utils.getFontTypeFace
import com.yariksoffice.lingver.Lingver
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingFragment : HasDatabaseFragment() {

    private lateinit var languageList: List<Language>
    private val pref by lazy { AppSharedPref(requireContext()) }
    private val fontSizeArray = arrayOf("14", "16", "18", "20", "22", "24", "26")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageList = appDatabase.languageDao.getAllLanguages()
        initUi()
    }

    private fun initUi() {
        (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.settings)

        btnFontFamily.setOnClickListener { onFontFamilyClick(it) }
        btnFontSize.setOnClickListener { onFontSizeClick(it) }
        btnKeepScreenOn.setOnClickListener { onKeepScreenOnClick(it) }
        btnLanguage.setOnClickListener { onLanguageClick(it) }

        initSavedConfig()
    }

    private fun initSavedConfig() {
        setFontInfo()
        setLanguage()
        setKeepOnScreen()
    }

    private fun setLanguage() {
        btnLanguage.text = appDatabase.languageDao.getCurrentLanguage().name
    }

    private fun setKeepOnScreen() {
        val isKeepOnScreen = pref.isKeepScreenOn
        btnKeepScreenOn.text = getText(if (isKeepOnScreen) R.string.yes else R.string.no)
    }

    private fun onFontFamilyClick(v: View) {
        val fontFields = R.font::class.java.fields
        val menu = PopupMenu(requireContext(), v)
        fontFields.forEach { menu.menu.add(it.name) }
        menu.setOnMenuItemClickListener { menuItem ->
            val selectedFont = fontFields.first { it.name == menuItem.title }
            pref.changeSelectedFont(selectedFont.name, selectedFont.getInt(selectedFont))
            setFontInfo()
            return@setOnMenuItemClickListener true
        }
        menu.show()

    }

    private fun onFontSizeClick(v: View) {
        val menu = PopupMenu(requireContext(), v)
        fontSizeArray.forEach { menu.menu.add(it) }
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
        val menu = PopupMenu(requireContext(), v)
        languageList.forEach { menu.menu.add(it.name) }
        menu.setOnMenuItemClickListener { menuItem ->
            val selectedLang = languageList.first { it.name == menuItem.title }
            appDatabase.configDao.changeLocaleWithResource(selectedLang.id!!)
            Lingver.getInstance()
                .setLocale(requireContext(), selectedLang.key)
            requireActivity().recreate()
            return@setOnMenuItemClickListener true
        }
        menu.show()
    }

    private fun setFontInfo() {
        val fontSize = pref.fontSize
        btnFontSize.text = fontSize.toString()
        textViewExample.textSize = fontSize
        btnFontFamily.text = pref.getCurrentFontName()
        textViewExample.typeface =
            getFontTypeFace(requireContext(), pref.getCurrentFontResourceId())
    }

}