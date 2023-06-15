package com.sample.simpsonsviewer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.usecases.GetAllCharactersUseCase
import com.sample.simpsonsviewer.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var isInitialized = false

    lateinit var selectedCharacter: CharacterModel

    private val _allCharacters: MutableLiveData<UIState<List<CharacterModel>>> =
        MutableLiveData(UIState.LOADING)
    val allCharacters: LiveData<UIState<List<CharacterModel>>>
        get() = _allCharacters

    init {
        if (!isInitialized) {
            getAllCharacters()
            isInitialized = true
        }
    }

    fun getAllCharacters() {
        viewModelScope.launch(ioDispatcher) {
            getAllCharactersUseCase().collect { result ->
                _allCharacters.postValue(result)
            }
        }
    }
}
