package com.example.marvelapp.framework.di

import android.content.Context
import com.example.core.data.repository.characters.CharactersRemoteDataSource
import com.example.core.data.repository.characters.CharactersRepository
import com.example.core.data.repository.storage.StorageRepository
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.GetCategoryUseCaseImpl
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetCharactersUseCaseImpl
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.sourceRemote.RetrofitCharactersDataSource
import org.koin.dsl.module

val useCaseCharacterModule = module {

    single<CharactersRemoteDataSource> { RetrofitCharactersDataSource(get<MarvelApi>()) }

    single<CharactersRepository> {
        CharactersRepositoryImpl(
            get<CharactersRemoteDataSource>(),
            provideAppDatabase(get<Context>())
        )
    }

    single<GetCharactersUseCase> {
        GetCharactersUseCaseImpl(
            get<CharactersRepository>(),
            get<StorageRepository>()
        )
    }

    single<GetCategoryUseCase> {
        GetCategoryUseCaseImpl(
            get<CharactersRepository>(),
            get<CoroutinesDispatchers>()
        )
    }
}


