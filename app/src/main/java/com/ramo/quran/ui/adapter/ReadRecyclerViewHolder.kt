package com.ramo.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.model.Versicle
import kotlinx.android.synthetic.main.recycler_read_item.view.*

class ReadRecyclerViewHolder(
    container: ViewGroup,
    private val fontSize: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context)
        .inflate(
            R.layout.recycler_read_item,
            container,
            false
        )
) {
    fun bind(versicle: Versicle) {
        with(itemView) {
            versicleNo.apply {
                textSize = fontSize.toFloat() + 1
                text = "${versicle.no}${context.getString(R.string.versicle)}"
            }
            this.versicle.apply {
                textSize = fontSize.toFloat()
                text = versicle.text
            }
        }
    }
}