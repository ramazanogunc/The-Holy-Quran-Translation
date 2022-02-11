package com.ramo.quran.di

import com.ramo.quran.data.dao.ConfigDao
import com.ramo.quran.data.dao.ResourceDao
import com.ramo.quran.data.dao.SurahNameDao
import com.ramo.quran.data.dao.VerseDao
import com.ramo.quran.data.repository.*
import com.ramo.quran.data.shared_pref.AppSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideVerseRepository(
        verseDao: VerseDao,
        sharedPref: AppSharedPref
    ) = VerseRepository(verseDao, sharedPref)

    @Provides
    fun provideSurahNameRepository(
        surahNameDao: SurahNameDao,
        verseDao: VerseDao,
        sharedPref: AppSharedPref
    ) = SurahNameRepository(surahNameDao, verseDao, sharedPref)

    @Provides
    fun provideResourceRepository(resourceDao: ResourceDao) = ResourceRepository(resourceDao)

    @Provides
    fun provideConfigRepository(configDao: ConfigDao) = ConfigRepository(configDao)

    @Provides
    fun provideFeedbackRepository() = FeedbackRepository()

}