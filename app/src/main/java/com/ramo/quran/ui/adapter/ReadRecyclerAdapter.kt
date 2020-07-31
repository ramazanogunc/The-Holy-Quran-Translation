package com.ramo.quran.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.model.Versicle

class ReadRecyclerAdapter(
    private val versicleList: List<Versicle>,
    private val fontSize: Int
): RecyclerView.Adapter<ReadRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).
                inflate(R.layout.recycler_read_item, parent,false)
        return ReadRecyclerViewHolder(view, fontSize)
    }

    override fun getItemCount(): Int {
        return versicleList.size
    }

    override fun onBindViewHolder(holder: ReadRecyclerViewHolder, position: Int) {
        holder.bind(versicleList.get(position))
    }
}