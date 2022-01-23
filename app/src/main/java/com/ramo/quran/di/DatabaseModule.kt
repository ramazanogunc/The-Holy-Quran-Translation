package com.ramo.quran.di

import android.content.Context
import com.ramo.quran.data.AppDatabase
import com.ramo.quran.data.dao.*
import com.ramo.quran.data.repository.VerseRepository
import com.ramo.quran.ext.AppSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
/*
    @Provides
    @Singleton
    fun provideAppSharedPref(@ApplicationContext context: Context): AppSharedPref {
        return AppSharedPref(context)
    }

 */
    @Provides
    fun provideSurahNameDao(appDatabase: AppDatabase): SurahNameDao = appDatabase.surahNameDao

    @Provides
    fun provideResourceDao(appDatabase: AppDatabase): ResourceDao = appDatabase.resourceDao

    @Provides
    fun provideConfigDao(appDatabase: AppDatabase): ConfigDao = appDatabase.configDao

    @Provides
    fun provideLanguageDao(appDatabase: AppDatabase): LanguageDao = appDatabase.languageDao

    @Provides
    fun provideVerseDao(appDatabase: AppDatabase): VerseDao = appDatabase.verseDao


    @Provides
    fun provideVerseRepository(verseDao: VerseDao) = VerseRepository(verseDao)

}