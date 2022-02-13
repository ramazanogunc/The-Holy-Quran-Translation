package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.db_translation.ResourceWithLanguage

@Dao
interface ResourceDao {

    @Query("SELECT * FROM resources")
    suspend fun getAllResourcesWithLanguage(): List<ResourceWithLanguage>
}