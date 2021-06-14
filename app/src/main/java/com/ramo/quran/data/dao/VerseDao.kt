package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.Verse

@Dao
interface VerseDao {

    @Query("SELECT * FROM verses WHERE resourceId=(SELECT currentResourceId FROM configs) AND surahNumber=:surahNumber")
    fun getCurrentSurahVerses(surahNumber: Int): List<Verse>
}