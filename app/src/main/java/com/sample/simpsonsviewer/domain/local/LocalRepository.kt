package com.sample.simpsonsviewer.domain.local

import android.util.Log
import com.sample.simpsonsviewer.domain.local.entities.CharacterEntity
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.domain.local.localmodel.mapFromEntityToCharacter
import com.sample.simpsonsviewer.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "LocalRepository"

interface LocalRepository {
    suspend fun insertCharacters(characters: List<CharacterEntity>?)
    fun getAllLocalCharacters(): Flow<UIState<List<CharacterModel>>>
}

class LocalRepositoryImpl(
    private val charactersDao: CharactersDao
) : LocalRepository {

    override suspend fun insertCharacters(characters: List<CharacterEntity>?) {
        charactersDao.insertCharacters(characters)
    }

    override fun getAllLocalCharacters(): Flow<UIState<List<CharacterModel>>> = flow {
        try {
            val characterInfoList = charactersDao.getAllLocalCharacters()
            Log.d(TAG, "getAllLocalCharacters: Fetching data from the database $characterInfoList")
            emit(UIState.SUCCESS(characterInfoList.mapFromEntityToCharacter()))

        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }
}
