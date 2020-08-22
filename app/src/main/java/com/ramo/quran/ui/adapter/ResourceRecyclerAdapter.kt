package com.ramo.quran.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ramo.quran.model.Resource

class ResourceRecyclerAdapter(
    private val resourceOnClickEvent: (resource: Resource) -> Unit
) : ListAdapter<Resource, ResourceViewHolder>(ResourceRecyclerCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResourceViewHolder(parent, resourceOnClickEvent)

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class ResourceRecyclerCallback : DiffUtil.ItemCallback<Resource>() {
    override fun areItemsTheSame(oldItem: Resource, newItem: Resource) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Resource, newItem: Resource) = oldItem.id == newItem.id

}