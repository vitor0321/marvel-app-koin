package com.example.marvelapp.framework.di

import com.example.marvelapp.presentation.ui.activity.MainActivity
import com.example.marvelapp.presentation.ui.fragment.about.AboutFragment
import com.example.marvelapp.presentation.ui.fragment.characters.CharactersFragment
import com.example.marvelapp.presentation.ui.fragment.favorites.FavoritesFragment
import org.koin.dsl.module

val uiModule = module {

    factory<MainActivity> { MainActivity() }
    factory<AboutFragment> { AboutFragment() }
    factory<CharactersFragment> { CharactersFragment() }
    factory<FavoritesFragment> { FavoritesFragment() }
}