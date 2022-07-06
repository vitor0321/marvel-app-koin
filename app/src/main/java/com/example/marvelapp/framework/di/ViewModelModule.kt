package com.example.marvelapp.framework.di

import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.CheckFavoriteUseCase
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.GetCharactersSortingUseCase
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetFavoritesUseCase
import com.example.core.usecase.RemoveFavoriteUseCase
import com.example.core.usecase.SaveCharactersSortingUseCase
import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.presentation.fragment.characters.CharactersViewModel
import com.example.marvelapp.presentation.fragment.detail.DetailViewModel
import com.example.marvelapp.presentation.fragment.favorites.FavoritesViewModel
import com.example.marvelapp.presentation.sort.SortViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<CharactersViewModel> {
        CharactersViewModel(
            get<GetCharactersUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }
    viewModel<DetailViewModel> {
        DetailViewModel(
            get<GetCategoryUseCase>(),
            get<CheckFavoriteUseCase>(),
            get<AddFavoriteUseCase>(),
            get<RemoveFavoriteUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }
    viewModel<FavoritesViewModel> {
        FavoritesViewModel(
            get<GetFavoritesUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    viewModel<SortViewModel> {
        SortViewModel(
            get<GetCharactersSortingUseCase>(),
            get<SaveCharactersSortingUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }
}