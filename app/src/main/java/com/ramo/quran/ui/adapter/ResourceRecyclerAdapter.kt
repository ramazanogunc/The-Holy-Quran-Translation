package com.ramo.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.model.Resource

class ResourceRecyclerAdapter(
    private val resourceList:List<Resource>,
    private val  onResourceClickListener: onResourceClickListener
): RecyclerView.Adapter<ResourceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_resource_item, parent, false)
        return ResourceViewHolder(v,onResourceClickListener)
    }

    override fun getItemCount(): Int {
        return resourceList.size
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resourceList[position])
    }


}
interface onResourceClickListener {
    fun onResourceClick(resource:Resource)
}