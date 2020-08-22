package com.ramo.quran.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ramo.quran.model.Versicle

class ReadRecyclerAdapter(
    private val fontSize: Int
) : ListAdapter<Versicle, ReadRecyclerViewHolder>(ReadRecyclerCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReadRecyclerViewHolder(parent, fontSize)

    override fun onBindViewHolder(holder: ReadRecyclerViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class ReadRecyclerCallback : DiffUtil.ItemCallback<Versicle>() {
    override fun areItemsTheSame(oldItem: Versicle, newItem: Versicle) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Versicle, newItem: Versicle) = oldItem.id == newItem.id
}
