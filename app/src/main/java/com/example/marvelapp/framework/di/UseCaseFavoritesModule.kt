package com.example.marvelapp.framework.di

import com.example.core.data.repository.favorites.FavoritesLocalDataSource
import com.example.core.data.repository.favorites.FavoritesRepository
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.AddFavoriteUseCaseImpl
import com.example.core.usecase.CheckFavoriteUseCase
import com.example.core.usecase.CheckFavoriteUseCaseImpl
import com.example.core.usecase.GetFavoritesUseCase
import com.example.core.usecase.GetFavoritesUseCaseImpl
import com.example.core.usecase.RemoveFavoriteUseCase
import com.example.core.usecase.RemoveFavoriteUseCaseImpl
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.framework.FavoritesRepositoryImpl
import com.example.marvelapp.framework.db.dao.FavoriteDao
import com.example.marvelapp.framework.sourceLocal.RoomFavoriteDataSource
import org.koin.dsl.module

val useCaseFavoritesModule = module {

    single<FavoritesRepository> { FavoritesRepositoryImpl(get<FavoritesLocalDataSource>()) }

    single<FavoritesLocalDataSource> { RoomFavoriteDataSource(get<FavoriteDao>()) }

    single<AddFavoriteUseCase> {
        AddFavoriteUseCaseImpl(
            get<FavoritesRepository>(),
            get<CoroutinesDispatchers>()
        )
    }

    single<CheckFavoriteUseCase> {
        CheckFavoriteUseCaseImpl(
            get<FavoritesRepository>(),
            get<CoroutinesDispatchers>()
        )
    }

    single<RemoveFavoriteUseCase> {
        RemoveFavoriteUseCaseImpl(
            get<FavoritesRepository>(),
            get<CoroutinesDispatchers>()
        )
    }

    single<GetFavoritesUseCase> {
        GetFavoritesUseCaseImpl(
            get<FavoritesRepository>(),
            get<CoroutinesDispatchers>()
        )
    }
}