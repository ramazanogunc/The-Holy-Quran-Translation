package com.ramo.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.ramo.quran.R
import com.ramo.quran.helper.AppSharedPref
import com.ramo.quran.helper.showSuccess
import com.ramo.quran.model.Language
import com.ramo.quran.ui.MainActivity
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

        btnFontSize.setOnClickListener { onFontSizeClick(it) }
        btnLanguage.setOnClickListener { onLanguageClick(it) }

        initSavedConfig()
    }

    private fun initSavedConfig() {
        setFontSize()
        setLanguage()
    }

    private fun setLanguage() {
        btnLanguage.text = appDatabase.languageDao.getCurrentLanguage().name
    }

    private fun onFontSizeClick(v: View) {
        val menu = PopupMenu(requireContext(), v)
        fontSizeArray.forEach { menu.menu.add(it) }
        menu.setOnMenuItemClickListener {
            val fontSize = it.title.toString().toFloat()
            pref.changeFontSize(fontSize)
            requireActivity().showSuccess()
            setFontSize()
            return@setOnMenuItemClickListener true
        }
        menu.show()
    }

    private fun onLanguageClick(v: View) {
        val menu = PopupMenu(requireContext(), v)
        languageList.forEach { menu.menu.add(it.name) }
        menu.setOnMenuItemClickListener { menuItem ->
            val selectedLang = languageList.filter { it.name == menuItem.title }.first()
            appDatabase.configDao.changeLocaleWithResource(selectedLang.id!!)
            Lingver.getInstance()
                .setLocale(requireContext(), selectedLang.key)
            requireActivity().recreate()
            return@setOnMenuItemClickListener true
        }
        menu.show()
    }

    private fun setFontSize() {
        val fontSize = pref.getFontSize()
        btnFontSize.text = fontSize.toString()
        textViewExample.textSize = fontSize
    }

}