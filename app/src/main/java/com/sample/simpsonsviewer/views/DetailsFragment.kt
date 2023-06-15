package com.sample.simpsonsviewer.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.databinding.FragmentDetailsBinding
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel
import com.sample.simpsonsviewer.utils.BaseFragment
import java.util.regex.Pattern

private const val BASE_IMAGE_URL = "https://duckduckgo.com"

class DetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var selectedCharacter: CharacterModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        selectedCharacter = charactersViewModel.selectedCharacter

        val pattern = Pattern.compile("<.*?>(.*?)</a>")
        val matcher = pattern.matcher(selectedCharacter.name)

        if (matcher.find()) {
            val name = matcher.group(1)
            binding.characterNameTextview.text = name
        } else {
            binding.characterNameTextview.text = selectedCharacter.name
        }

        val characterTextString = selectedCharacter.text
        // second element without surrounding whitespace
        val splitCharacterText = characterTextString.split("-")
        val characterTextResult = splitCharacterText[1].trim()

        binding.characterDescriptionTextview.text = characterTextResult

        Glide
            .with(binding.root)
            .load(BASE_IMAGE_URL + selectedCharacter.icon.url)
            .centerCrop()
            .override(400, 600)
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_off_24)
            .into(binding.characterThumbnailImageview)

        return binding.root
    }
}
