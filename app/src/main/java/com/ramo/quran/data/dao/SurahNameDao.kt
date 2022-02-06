package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.db_translation.SurahName

@Dao
interface SurahNameDao {

    @Query("SELECT * FROM surahNames WHERE languageId=(SELECT language FROM configs, resources as r WHERE r.id=currentResourceId) AND number=:surahNumber")
    suspend fun getCurrentSurahName(surahNumber: Int): SurahName

    @Query("SELECT * FROM surahNames WHERE languageId=(SELECT language FROM configs, resources as r WHERE r.id=currentResourceId)")
    suspend fun getAllSurahName(): List<SurahName>
}