package com.example.marvelapp.presentation.fragment.characters.adapters

import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.core.domain.model.Character
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.util.OnCharacterItemClick

sealed class ViewItem {

    data class CharactersStart(
        val character: Character,
    ) : ViewItem()

    data class CharactersMiddle(
        val character: Character,
    ) : ViewItem()

    data class CharactersEnd(
        val character: Character,
    ) : ViewItem()
}
class CharactersAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
) : PagingDataAdapter<Character, CharactersMiddleViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersMiddleViewHolder {
        return CharactersMiddleViewHolder.create(parent, imageLoader, onItemClick)
    }

    override fun onBindViewHolder(holder: CharactersMiddleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}