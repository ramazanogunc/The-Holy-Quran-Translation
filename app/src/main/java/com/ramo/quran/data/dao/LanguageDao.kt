package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.db_translation.Language

@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages")
    suspend fun getAllLanguages(): List<Language>

    @Query("SELECT * FROM languages WHERE id=(SELECT languageId FROM configs)")
    suspend fun getCurrentLanguage(): Language

}