package com.sample.simpsonsviewer.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.databinding.FragmentCharacterListBinding
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.utils.BaseFragment
import com.sample.simpsonsviewer.utils.UIState
import com.sample.simpsonsviewer.views.adapters.CharactersAdapter

private const val TAG = "CharacterList"

class CharactersListFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCharacterListBinding.inflate(layoutInflater)
    }

    private val charactersListAdapter: CharactersAdapter by lazy {
        CharactersAdapter {
            charactersViewModel.selectedCharacter = it
            findNavController().navigate(R.id.action_characterList_to_detailsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val characterList: MutableSet<CharacterModel> = mutableSetOf()

        binding.charactersListRecyclerview.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = charactersListAdapter
        }

        charactersViewModel.allCharacters.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    characterList.clear()
                    characterList.addAll(state.response)

                    Log.d(TAG, "onCreateView: ${characterList.size}")
                    charactersListAdapter.updateItems(characterList)
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

        binding.characterSearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = characterList.filter { character ->
                    character.text.contains(newText.toString(), true)
                }.toMutableSet()

                Log.d(TAG, "onQueryTextChange: ${filteredList.size}")
                charactersListAdapter.updateItems(filteredList)
                return true
            }
        })

        return binding.root
    }
}
