package com.ramo.quran.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "languages"
)
data class Language(
    val name: String,
    val key: String
) {
    @PrimaryKey
    var id: Int? = null
}