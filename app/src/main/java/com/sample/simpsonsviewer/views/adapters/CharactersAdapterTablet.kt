package com.sample.simpsonsviewer.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.databinding.FragmentDetailsTabletBinding
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel

private const val TAG = "CharactersAdapterTablet"

class CharactersAdapterTablet(
    private val itemSet: MutableSet<CharacterModel> = mutableSetOf(),
    private val onItemClick: (previewCharacterCard: CharacterModel) -> Unit
) : RecyclerView.Adapter<CharactersTabletViewHolder>() {

    fun updateItems(newItems: MutableSet<CharacterModel>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            Log.d(TAG, "updateItems: $itemSet, newItems: $newItems")
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersTabletViewHolder {
        return CharactersTabletViewHolder(
            FragmentDetailsTabletBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersTabletViewHolder, position: Int) =
        holder.bind(itemSet.elementAt(position), onItemClick)

    override fun getItemCount(): Int = itemSet.size
}

class CharactersTabletViewHolder(
    private val binding: FragmentDetailsTabletBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterModel, onItemClick: (previewCharacterCard: CharacterModel) -> Unit) {
        binding.characterNameTextview.text = item.name

        val characterTextString = item.text
        // second element without surrounding whitespace
        val splitCharacterText = characterTextString.split("-")
        val characterTextResult = splitCharacterText[1].trim()

        binding.characterDescription.text = characterTextResult

        Glide
            .with(binding.root)
            .load("https://duckduckgo.com" + item.icon.url)
            .centerCrop()
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_off_24)
            .into(binding.characterThumbnailImageview)
    }
}
