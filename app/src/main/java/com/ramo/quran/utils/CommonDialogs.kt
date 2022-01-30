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
import com.ramo.quran.core.ext.gone
import com.ramo.sweetrecycleradapter.SweetRecyclerAdapter
import com.ramo.sweetrecycleradapter.ViewTypeListener


object CommonDialogs {

    fun selectDialog(
        context: Context,
        model: SelectDialogModel,
        onSelect: (Int) -> Unit,
        onDismiss: (() -> Unit)? = null
    ) {
        val dialog = getConfigureDialog(context, R.layout.dialog_select)
        dialog.setOnDismissListener { onDismiss?.invoke() }
        val adapter = SweetRecyclerAdapter<StringRecyclerItem>()
        adapter.addHolder(R.layout.dialog_item_select) { v, item ->
            v.findViewById<TextView>(R.id.txtName).text = item.name
        }
        adapter.setOnItemClickListener { _, item ->
            onSelect.invoke(model.items.indexOf(item.name))
            dialog.dismiss()
        }
        with(dialog) {
            findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
            findViewById<TextView>(R.id.txtTitle).text = model.title
            findViewById<TextView>(R.id.txtDescription).text = model.description
            if (model.description == null) findViewById<TextView>(R.id.txtDescription).gone()
            findViewById<RecyclerView>(R.id.rvItems).adapter = adapter

        }
        adapter.submitList(model.items.map { StringRecyclerItem(it) })
        dialog.show()

    }

    private fun getConfigureDialog(context: Context, @LayoutRes layoutId: Int): Dialog {
        val dialog = Dialog(context).apply {
            setContentView(layoutId)
            setCanceledOnTouchOutside(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }
}

data class SelectDialogModel(
    val title: String,
    val description: String? = null,
    val items: List<String>
)

data class StringRecyclerItem(
    val name: String
) : ViewTypeListener {
    override fun getRecyclerItemLayoutId(): Int = R.layout.dialog_item_select
}
