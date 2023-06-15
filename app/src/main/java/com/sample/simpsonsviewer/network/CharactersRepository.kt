package com.sample.simpsonsviewer.network

import android.util.Log
import com.sample.simpsonsviewer.domain.local.CharactersDao
import com.sample.simpsonsviewer.domain.local.entities.mapToCharacterEntity
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.domain.local.localmodel.mapFromEntityToCharacter
import com.sample.simpsonsviewer.utils.FailureResponse
import com.sample.simpsonsviewer.utils.NullCharacterListResponse
import com.sample.simpsonsviewer.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "SimpsonsRepository"

interface CharactersRepository {
     fun getAllCharacters(): Flow<UIState<List<CharacterModel>>>
}

class CharactersRepositoryImpl @Inject constructor(
    private val charactersServiceApi: CharactersServiceApi,
    private val charactersDao: CharactersDao
): CharactersRepository {

    override fun getAllCharacters(): Flow<UIState<List<CharacterModel>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = charactersServiceApi.getAllCharacters()

            if(response.isSuccessful) {
                response.body()?.let {
                    val characterInfoList = it.relatedTopics
                    Log.d(TAG, "getAllCharacters: Inserting characters in database, $characterInfoList")
                    charactersDao.insertCharacters(characterInfoList.mapToCharacterEntity())

                    val newCharacterInfoList = charactersDao.getAllLocalCharacters()
                    Log.d(TAG, "getAllCharacters: Get all Characters SUCCESS $newCharacterInfoList")
                    emit(UIState.SUCCESS(newCharacterInfoList.mapFromEntityToCharacter()))

                } ?: throw NullCharacterListResponse()

            } else {
                throw FailureResponse(response.errorBody()?.string())
            }

        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }
}
