package com.sample.simpsonsviewer.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.simpsonsviewer.databinding.FragmentCharacterListTabletBinding
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.utils.BaseFragment
import com.sample.simpsonsviewer.utils.UIState
import com.sample.simpsonsviewer.views.adapters.CharactersAdapterTablet

private const val TAG = "CharacterListTabletFrag"

class CharactersListTabletFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCharacterListTabletBinding.inflate(layoutInflater)
    }

    private val charactersListAdapterTablet: CharactersAdapterTablet by lazy {
        CharactersAdapterTablet {
            charactersViewModel.selectedCharacter = it
        }
    }

    private val charactersList = mutableSetOf<CharacterModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.charactersListRecyclerviewTablet.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = charactersListAdapterTablet
        }

        charactersViewModel.allCharacters.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    charactersList.clear()
                    charactersList.addAll(state.response)

                    charactersListAdapterTablet.updateItems(charactersList)
                }

                is UIState.ERROR -> {
                    state.error.localizedMessage?.let {
                        showError(it) {
                            Log.d(TAG, "onCreateView: UIState Error: $state")
                        }
                    }
                }
            }
        }

        binding.charactersSearchviewTablet.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = mutableSetOf<CharacterModel>()

                if (newText.isNullOrBlank()) {
                    filteredList.addAll(charactersList)

                } else {
                    charactersList.forEach { character ->
                        if (character.text.contains(newText.toString(), true)) {
                            filteredList.add(character)
                        }
                    }
                }

                charactersListAdapterTablet.updateItems(filteredList)

                return true
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        charactersList.clear()
    }
}
