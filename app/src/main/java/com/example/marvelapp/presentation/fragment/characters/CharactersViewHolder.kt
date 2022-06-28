package com.example.marvelapp.presentation.fragment.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Character
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.util.OnCharacterItemClick

class CharactersViewHolder(
    itemCharactersBinding: ItemCharacterBinding,
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
) : RecyclerView.ViewHolder(itemCharactersBinding.root) {

    private val textName = itemCharactersBinding.textName
    private val imageCharacter = itemCharactersBinding.imageCharacter

    fun bind(character: Character) {
        textName.text = character.name
        imageCharacter.transitionName = character.name
        imageLoader.load(imageCharacter, character.imageUrl)

        itemView.setOnClickListener {
            onItemClick.invoke(character, imageCharacter)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            onItemClick: OnCharacterItemClick
        ): CharactersViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parent, false)
            return CharactersViewHolder(itemBinding, imageLoader, onItemClick)
        }
    }
}