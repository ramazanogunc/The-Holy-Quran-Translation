package com.ramo.quran.model.db_translation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "configs",
    foreignKeys = [
        ForeignKey(
            entity = Resource::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("currentResourceId")
        ),
        ForeignKey(
            entity = Language::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("languageId")
        )
    ]
)
data class Config(
    val currentResourceId: Int,
    val languageId: Int
) {
    @PrimaryKey
    var id: Int? = null
}

