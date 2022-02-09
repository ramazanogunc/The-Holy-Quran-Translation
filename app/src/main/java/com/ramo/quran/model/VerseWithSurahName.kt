package com.ramo.quran.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ramo.quran.R
import com.ramo.quran.model.db_translation.Resource
import com.ramo.sweetrecycleradapter.ViewTypeListener

@Entity(
    tableName = "verses",
    foreignKeys = [
        ForeignKey(
            entity = Resource::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("resourceId")
        )
    ]
)
data class VerseWithSurahName(
    val verseNo: Int,
    val surahNumber: Int,
    val verse: String,
    val resourceId: Int,
    val surahName: String = "Deneme"
) : ViewTypeListener {

    @PrimaryKey
    var id: Int? = null

    override fun getRecyclerItemLayoutId() = R.layout.recycler_search_item

}
