package com.example.marvelapp.presentation.fragment.favorites

import com.example.marvelapp.presentation.common.adapterGeneric.ListItem

data class FavoriteItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    override val key: Long = id.toLong()
) : ListItem
