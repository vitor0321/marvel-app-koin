package com.example.marvelapp.framework.di

import com.example.core.data.repository.favorites.FavoritesLocalDataSource
import com.example.core.data.repository.favorites.FavoritesRepository
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.AddFavoriteUseCaseImpl
import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.framework.FavoritesRepositoryImpl
import com.example.marvelapp.framework.db.dao.FavoriteDao
import com.example.marvelapp.framework.sourceLocal.RoomFavoriteDataSource
import org.koin.dsl.module

val favoritesRepositoryModule = module {

    single<FavoritesRepository> { FavoritesRepositoryImpl(get<FavoritesLocalDataSource>()) }

    single<FavoritesLocalDataSource> { RoomFavoriteDataSource(get<FavoriteDao>()) }

    single<AddFavoriteUseCase> {
        AddFavoriteUseCaseImpl(
            get<FavoritesRepository>(),
            get<CoroutinesDispatchers>()
        )
    }
}