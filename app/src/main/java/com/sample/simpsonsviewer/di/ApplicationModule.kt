package com.sample.simpsonsviewer.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.sample.simpsonsviewer.domain.local.CharactersDatabase
import com.sample.simpsonsviewer.domain.local.CharactersDao
import com.sample.simpsonsviewer.domain.local.LocalRepository
import com.sample.simpsonsviewer.domain.local.LocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun providesSimpsonsDao(
        @ApplicationContext context: Context
    ): CharactersDao =
        Room.databaseBuilder(
            context,
            CharactersDatabase::class.java,
            "simpsons-db"
        ).build().charactersDao

    @Provides
    fun providesLocalRepository(
        charactersDao: CharactersDao
    ): LocalRepository =
        LocalRepositoryImpl(charactersDao)
}
