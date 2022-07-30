package com.example.marvelapp.presentation.fragment.characters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterEndBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.extensions.animationInfinitely
import com.example.marvelapp.presentation.common.extensions.startChameleonCorAnim
import com.example.marvelapp.presentation.common.util.OnCharacterItemClick

class CharactersEndViewHolder(
    itemCharactersEndBinding: ItemCharacterEndBinding,
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
) : RecyclerView.ViewHolder(itemCharactersEndBinding.root) {

    private val textName = itemCharactersEndBinding.textName
    private val imageCharacter = itemCharactersEndBinding.imageCharacter
    private val imageClickDetail = itemCharactersEndBinding.imageClickDetail
    fun bind(character: Character) {
        textName.text = character.name
        imageCharacter.transitionName = character.name
        imageClickDetail.setImageResource(R.drawable.ic_detail_click)
        startChameleonCorAnim(textName)
        animationInfinitely(imageClickDetail, R.color.character_item_detail_click)
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
        ): CharactersEndViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterEndBinding.inflate(inflater, parent, false)
            return CharactersEndViewHolder(itemBinding, imageLoader, onItemClick)
        }
    }
}