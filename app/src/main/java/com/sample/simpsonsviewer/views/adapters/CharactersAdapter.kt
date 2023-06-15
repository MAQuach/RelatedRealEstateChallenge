package com.sample.simpsonsviewer.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.simpsonsviewer.databinding.CharacterItemBinding
import com.sample.simpsonsviewer.domain.local.localmodel.CharacterModel

private const val TAG = "CharactersAdapter"

class CharactersAdapter(
    private val itemSet: MutableSet<CharacterModel> = mutableSetOf(),
    private val onItemClick: (previewCharacterCard: CharacterModel) -> Unit
) : RecyclerView.Adapter<CharactersViewHolder>() {

    fun updateItems(newItems: MutableSet<CharacterModel>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            Log.d(TAG, "updateItems: $itemSet, newItems: $newItems")
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bind(itemSet.elementAt(position), onItemClick)

    override fun getItemCount(): Int = itemSet.size
}

class CharactersViewHolder(
    private val binding: CharacterItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CharacterModel, onItemClick: (previewCharacterCard: CharacterModel) -> Unit
    ) {
        binding.characterNameTextview.text = item.name

        itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}
