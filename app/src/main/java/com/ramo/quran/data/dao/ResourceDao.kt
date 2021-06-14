package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.ResourceWithLanguage

@Dao
interface ResourceDao {

    @Query("SELECT * FROM resources")
    fun getAllResourcesWithLanguage(): List<ResourceWithLanguage>
}