package com.ramo.quran.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.model.Resource

class ResourceViewHolder(
    view:View,
    private val  onResourceClickListenner: onResourceClickListener
):RecyclerView.ViewHolder(view) {
    private val languge = itemView.findViewById<TextView>(R.id.resourceLanguage)
    private val resource = itemView.findViewById<TextView>(R.id.resource)
    private val isCheck = itemView.findViewById<ImageView>(R.id.isCheck)

    fun bind(resource: Resource) {
        itemView.setOnClickListener{
            onResourceClickListenner.onResourceClick(resource)
        }
        languge.text = resource.language.name
        this.resource.text = resource.name
        isCheck.visibility = if (resource.isActive) View.VISIBLE else View.INVISIBLE
    }

}
