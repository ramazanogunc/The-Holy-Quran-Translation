package com.ramo.quran.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ramo.quran.data.dao.*
import com.ramo.quran.model.*

@Database(
    entities = [
        Config::class,
        Language::class,
        Resource::class,
        SurahName::class,
        Verse::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // todo: daolar eklenecek
    abstract val surahNameDao: SurahNameDao
    abstract val verseDao: VerseDao
    abstract val resourceDao: ResourceDao
    abstract val configDao: ConfigDao
    abstract val languageDao: LanguageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "kuran_v2.db"
            ).createFromAsset("database/kuran_v2.db")
                .allowMainThreadQueries()
                .build()
            INSTANCE = instance
            instance
        }
    }
}