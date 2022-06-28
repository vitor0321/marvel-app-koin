package com.example.marvelapp.framework.di

import com.example.core.data.repository.characters.CharactersRemoteDataSource
import com.example.core.data.repository.characters.CharactersRepository
import com.example.core.data.repository.favorites.FavoritesLocalDataSource
import com.example.core.data.repository.favorites.FavoritesRepository
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.GetCategoryUseCaseImpl
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetCharactersUseCaseImpl
import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.FavoritesRepositoryImpl
import com.example.marvelapp.framework.db.dao.FavoriteDao
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.sourceLocal.RoomFavoriteDataSource
import com.example.marvelapp.framework.sourceRemote.RetrofitCharactersDataSource
import com.example.marvelapp.presentation.fragment.favorites.FavoritesFragment
import org.koin.dsl.module

val useCaseCharacterModule = module {

    single<CharactersRemoteDataSource> { RetrofitCharactersDataSource(get<MarvelApi>()) }

    single<CharactersRepository> { CharactersRepositoryImpl(get<CharactersRemoteDataSource>()) }

    single<GetCharactersUseCase>{GetCharactersUseCaseImpl(get<CharactersRepository>())}

    single<GetCategoryUseCase> {
        GetCategoryUseCaseImpl(
            get<CharactersRepository>(),
            get<CoroutinesDispatchers>()
        )
    }
}


