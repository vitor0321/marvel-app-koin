package com.example.marvelapp.presentation.fragment.characters.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.util.OnCharacterItemClick

class CharactersAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: OnCharacterItemClick
) : PagingDataAdapter<Character, CharactersViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder.create(parent, imageLoader, onItemClick)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val start = R.drawable.marvel_character_start
        val middle = R.drawable.marvel_character_middle
        val end = R.drawable.marvel_character_end
        when {
            position == 0 -> getItem(position)?.let { holder.bind(it, start) }
            position > 0 -> getItem(position)?.let { holder.bind(it, middle) }
            position == itemCount -1 -> getItem(position)?.let { holder.bind(it, end) }
        }
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