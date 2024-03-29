package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.activity.MainActivity
import com.example.marvelapp.presentation.common.util.OnCharacterItemClick
import com.example.marvelapp.presentation.fragment.about.AboutFragment
import com.example.marvelapp.presentation.fragment.characters.CharactersFragment
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersAdapter
import com.example.marvelapp.presentation.fragment.detail.DetailFragment
import com.example.marvelapp.presentation.fragment.favorites.FavoritesFragment
import com.example.marvelapp.presentation.fragment.intro.IntroFragment
import com.example.marvelapp.presentation.fragment.sort.SortFragment
import org.koin.dsl.module

val uiModule = module {
    factory<CharactersAdapter> {
        CharactersAdapter(
            get<ImageLoader>(),
            get<OnCharacterItemClick>()
        )
    }
    factory<MainActivity> { MainActivity() }
    factory<AboutFragment> { AboutFragment() }
    factory<CharactersFragment> { CharactersFragment() }
    factory<FavoritesFragment> { FavoritesFragment() }
    factory<DetailFragment> { DetailFragment() }
    factory<SortFragment> { SortFragment() }
    factory<IntroFragment> { IntroFragment() }
}