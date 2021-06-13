package com.ramo.quran.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "resources",
    foreignKeys = [
        ForeignKey(
            entity = Language::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("language")
        )
    ]
)
data class Resource(
    val name: String,
    val language: Int
) {
    @PrimaryKey
    var id: Int? = null
}
