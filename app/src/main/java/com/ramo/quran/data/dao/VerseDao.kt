package com.ramo.quran.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.VerseWithSurahName
import com.ramo.quran.model.db_translation.Verse

@Dao
interface VerseDao {

    @Query("SELECT * FROM verses WHERE resourceId=(SELECT currentResourceId FROM configs) AND surahNumber=:surahNumber")
    suspend fun getCurrentSurahVerses(surahNumber: Int): List<Verse>

    @Query("SELECT COUNT() FROM verses WHERE resourceId=(SELECT currentResourceId FROM configs) GROUP BY surahNumber")
    suspend fun getAllSurahVerseNumbers(): List<Int>

    @Query("SELECT * FROM verses WHERE resourceId=(SELECT currentResourceId FROM configs) AND surahNumber=:surahNumber AND verseNo=:verseNumber")
    suspend fun getVerse(surahNumber: Int, verseNumber: Int): Verse

    @Query("SELECT *, (SELECT name FROM surahNames WHERE languageId=(SELECT language FROM configs, resources as r WHERE r.id=currentResourceId) AND number=verses.surahNumber) as surahName FROM verses WHERE resourceId=(SELECT currentResourceId FROM configs) AND verse LIKE '%' || :text || '%'  LIMIT :perPage OFFSET ((:perPage*:page)-:page)")
    suspend fun searchVerse(text: String, page: Int, perPage: Int): List<VerseWithSurahName>

    @Query("SELECT *, (SELECT name FROM surahNames WHERE languageId=(SELECT language FROM configs, resources as r WHERE r.id=currentResourceId) AND number=verses.surahNumber) as surahName FROM verses WHERE resourceId=(SELECT currentResourceId FROM configs) AND verse LIKE '%' || :text || '%'")
    fun searchVerse(text: String): PagingSource<Int, VerseWithSurahName>
}