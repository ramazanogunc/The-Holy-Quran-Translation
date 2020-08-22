package com.ramo.quran.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.model.Resource
import kotlinx.android.synthetic.main.recycler_resource_item.view.*

class ResourceViewHolder(
    container: ViewGroup,
    private val resourceOnClickEvent: (resource: Resource) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context)
        .inflate(
            R.layout.recycler_resource_item,
            container,
            false
        )
) {
    fun bind(resource: Resource) {
        with(itemView) {
            setOnClickListener {
                resourceOnClickEvent(resource)
            }
            resourceLanguage.text = resource.language.name
            this.resource.text = resource.name
            isCheck.visibility = if (resource.isActive) View.VISIBLE else View.INVISIBLE
        }
    }

}
