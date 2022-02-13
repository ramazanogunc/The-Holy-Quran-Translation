package com.ramo.quran.model.db_translation

import androidx.room.Embedded
import androidx.room.Relation
import com.ramo.quran.R
import com.ramo.sweetrecycleradapter.ViewTypeListener

data class ResourceWithLanguage(
    @Embedded val resource: Resource,

    @Relation(parentColumn = "language", entityColumn = "id", entity = Language::class)
    val language: Language
) : ViewTypeListener {
    override fun getRecyclerItemLayoutId(): Int = R.layout.recycler_resource_item
}
