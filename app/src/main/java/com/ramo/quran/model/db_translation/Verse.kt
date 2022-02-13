package com.ramo.quran.model.db_translation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ramo.quran.R
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
data class Verse(
    val verseNo: Int,
    val surahNumber: Int,
    val verse: String,
    val resourceId: Int
) : ViewTypeListener {

    @PrimaryKey
    var id: Int? = null

    override fun getRecyclerItemLayoutId(): Int = R.layout.recycler_read_item
}
