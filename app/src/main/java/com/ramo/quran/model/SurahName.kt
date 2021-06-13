package com.ramo.quran.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
) {
    @PrimaryKey
    var id: Int? = null
}
