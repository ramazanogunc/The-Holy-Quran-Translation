package com.ramo.quran.data.dao.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.SurahName

@Dao
interface SurahNameDao {

    @Query("SELECT * FROM surahNames WHERE languageId=(SELECT languageId FROM configs) AND number=:surahNumber")
    fun getCurrentSurahName(surahNumber: Int): SurahName

    @Query("SELECT * FROM surahNames WHERE languageId=(SELECT languageId FROM configs)")
    fun getAllSurahName(): List<SurahName>
}