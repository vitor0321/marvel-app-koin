package com.example.marvelapp.framework.di

import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetComicsUseCase
import com.example.marvelapp.presentation.fragment.characters.CharactersViewModel
import com.example.marvelapp.presentation.fragment.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<CharactersViewModel> { CharactersViewModel(get<GetCharactersUseCase>()) }
    viewModel<DetailViewModel> { DetailViewModel(get<GetComicsUseCase>()) }
}