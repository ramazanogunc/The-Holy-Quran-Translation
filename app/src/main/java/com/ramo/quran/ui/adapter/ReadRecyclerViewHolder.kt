package com.ramo.quran.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.model.Versicle

class ReadRecyclerViewHolder(itemView: View, private val fontSize: Int) : RecyclerView.ViewHolder(itemView) {
    private val textViewVersicleNo = itemView.findViewById<TextView>(R.id.versicleNo)
    private val textViewVersicle = itemView.findViewById<TextView>(R.id.versicle)
    fun bind (versicle: Versicle){
        textViewVersicleNo.textSize = fontSize.toFloat()+1
        textViewVersicle.textSize = fontSize.toFloat()
        textViewVersicleNo.text = versicle.no.toString()+ itemView.context.getString(R.string.versicle)
        textViewVersicle.text = versicle.text
    }
}
