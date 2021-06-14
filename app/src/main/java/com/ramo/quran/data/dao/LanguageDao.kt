package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.Language

@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages")
    fun getAllLanguages(): List<Language>

    @Query("SELECT * FROM languages WHERE id=(SELECT languageId FROM configs)")
    fun getCurrentLanguage(): Language

}