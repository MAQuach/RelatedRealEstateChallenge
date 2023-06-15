package com.sample.simpsonsviewer.di

import com.sample.simpsonsviewer.network.CharactersRepository
import com.sample.simpsonsviewer.network.CharactersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesCharactersRepositoryImpl(charactersRepositoryImpl: CharactersRepositoryImpl):
            CharactersRepository
}
