package com.ramo.quran.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ramo.quran.R
import com.ramo.sweetrecycleradapter.ViewTypeListener

@Entity(
    tableName = "surahNames",
    foreignKeys = [
        ForeignKey(
            entity = Language::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("languageId")
        )
    ]
)
data class SurahName(
    val number: Int,
    val name: String,
    val languageId: Int
): ViewTypeListener {
    @PrimaryKey
    var id: Int? = null
    override fun getRecyclerItemLayoutId(): Int  = R.layout.item_select_surah
}
