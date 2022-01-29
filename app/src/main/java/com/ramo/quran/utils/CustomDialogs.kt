package com.ramo.quran.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.ramo.quran.R
import com.ramo.quran.model.SurahName
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter


object CustomDialogs {

    fun changeSurah(
        context: Context,
        surahNames: List<SurahName>,
        onSelect: (SurahName) -> Unit,
        onDismiss: () -> Unit
    ) {
        val dialog: Dialog = getConfigureDialog(context, R.layout.dialog_select_surah)
        dialog.setOnDismissListener { onDismiss.invoke() }
        val rv = dialog.findViewById<RecyclerView>(R.id.rvSurahs)
        dialog.findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
        val adapter = SweetRecyclerAdapter<SurahName>()
        adapter.addHolder(R.layout.item_select_surah) { v, item ->
            v.findViewById<TextView>(R.id.txtSurahName).text = item.name
        }
        adapter.setOnItemClickListener { _, item ->
            onSelect.invoke(item)
            dialog.dismiss()
        }
        rv.adapter = adapter
        adapter.submitList(surahNames)
        dialog.show()
    }

    private fun getConfigureDialog(context: Context, @LayoutRes layoutId: Int): Dialog =
        Dialog(context).apply {
            setContentView(layoutId)
            setCanceledOnTouchOutside(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
}