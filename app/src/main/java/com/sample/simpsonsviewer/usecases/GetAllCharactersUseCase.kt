package com.sample.simpsonsviewer.usecases

import android.util.Log
import com.sample.simpsonsviewer.domain.local.LocalRepository
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.network.CharactersRepository
import com.sample.simpsonsviewer.network.CharactersServiceApi
import com.sample.simpsonsviewer.utils.NetworkState
import com.sample.simpsonsviewer.utils.UIState
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

private const val TAG = "GetAllSimpsonsCharacter"

class GetAllCharactersUseCase @Inject constructor(
    private val charactersServiceApi: CharactersServiceApi,
    private val localRepository: LocalRepository,
    private val charactersRepository: CharactersRepository,
    private val networkState: NetworkState,
) {

    operator fun invoke(): Flow<UIState<List<CharacterModel>>> {
        Log.d(TAG, "invoke: GetAllCharacters Use case")

        return if (!networkState.isInternetOn()) {
            Log.d(TAG, "invoke: Get All LOCAL CHARACTERS")
            localRepository.getAllLocalCharacters()

        } else {
            Log.d(TAG, "invoke: Get All API CHARACTERS")
            charactersRepository.getAllCharacters()
        }
    }
}
