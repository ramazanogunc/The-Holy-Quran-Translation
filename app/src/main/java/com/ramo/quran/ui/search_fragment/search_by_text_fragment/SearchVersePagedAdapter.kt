package com.ramo.quran.ui.search_fragment.search_by_text_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ramo.quran.R
import com.ramo.quran.databinding.RecyclerSearchItemBinding
import com.ramo.quran.model.VerseWithSurahName

class SearchVersePagedAdapter() :
    PagingDataAdapter<VerseWithSurahName, SearchVersePagedAdapter.MyViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class MyViewHolder(val itemBinding: RecyclerSearchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: VerseWithSurahName) {
            val context = itemBinding.root.context
            with(itemBinding) {
                verseNo.text = context.getString(R.string.verse_number, item.verseNo)
                surahName.text = context.getString(R.string.surah_name, item.surahName)
                verse.text = item.verse
            }
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<VerseWithSurahName>() {
            override fun areItemsTheSame(
                oldItem: VerseWithSurahName,
                newItem: VerseWithSurahName
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: VerseWithSurahName,
                newItem: VerseWithSurahName
            ): Boolean =
                newItem == oldItem
        }
    }

}