package com.ramo.quran.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.quran.model.Config

@Dao
interface ConfigDao {

    @Query("SELECT * FROM configs WHERE id=1")
    fun getConfig(): Config

    @Query("UPDATE configs SET currentResourceId=:resourceId WHERE id=1")
    fun updateCurrentResource(resourceId: Int)

    @Query("UPDATE configs SET languageId=:languageId, currentResourceId=(SELECT id FROM resources WHERE language=:languageId)")
    fun changeLocaleWithResource(languageId: Int)
}