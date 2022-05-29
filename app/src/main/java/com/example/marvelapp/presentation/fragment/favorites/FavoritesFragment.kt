package com.example.marvelapp.presentation.fragment.favorites

import com.example.marvelapp.databinding.FragmentFavoritesBinding
import com.example.marvelapp.presentation.fragment.BaseFragment

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override fun getViewBinding(): FragmentFavoritesBinding =
        FragmentFavoritesBinding.inflate(layoutInflater)
}