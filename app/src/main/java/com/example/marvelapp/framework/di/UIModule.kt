package com.example.marvelapp.framework.di

import com.example.marvelapp.presentation.activity.MainActivity
import com.example.marvelapp.presentation.fragment.about.AboutFragment
import com.example.marvelapp.presentation.fragment.characters.CharactersAdapter
import com.example.marvelapp.presentation.fragment.characters.CharactersFragment
import com.example.marvelapp.presentation.fragment.favorites.FavoritesFragment
import org.koin.dsl.module

val uiModule = module {
    factory<CharactersAdapter> { CharactersAdapter() }
    factory<MainActivity> { MainActivity() }
    factory<AboutFragment> { AboutFragment() }
    factory<CharactersFragment> { CharactersFragment() }
    factory<FavoritesFragment> { FavoritesFragment() }
}