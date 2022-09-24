package com.example.marvelapp.presentation.fragment.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.marvelapp.databinding.ItemCharacterFavoritesBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.adapterGeneric.GenericViewHolder

class FavoritesViewHolder(
    itemBinding: ItemCharacterFavoritesBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<FavoriteItem>(itemBinding) {

    private val textName: TextView = itemBinding.textName
    private val imageCharacter: ImageView = itemBinding.imageCharacter

    override fun bind(data: FavoriteItem) {
        textName.text = data.name
        imageLoader.load(imageCharacter, data.imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoritesViewHolder {
            val itemBinding = ItemCharacterFavoritesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return FavoritesViewHolder(itemBinding, imageLoader)
        }
    }
}