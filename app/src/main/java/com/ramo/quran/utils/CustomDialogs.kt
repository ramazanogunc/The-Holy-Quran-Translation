package com.ramo.quran.utils

import android.app.AlertDialog
import android.content.Context
import com.ramo.quran.R

object CustomDialogs {

    fun changeSurah(context: Context, surahNames: List<String>, onSelect: (Int) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.choice_surah))
        builder.setItems(
            surahNames.toTypedArray()
        ) { _, which ->
            onSelect.invoke(which)
        }
        val dialog = builder.create()
        dialog.show()
    }
}