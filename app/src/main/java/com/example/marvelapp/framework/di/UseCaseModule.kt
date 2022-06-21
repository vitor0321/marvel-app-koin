package com.example.marvelapp.framework.di

import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.data.repository.CharactersRepository
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.GetCategoryUseCaseImpl
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetCharactersUseCaseImpl
import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.remote.RetrofitCharactersDataSource
import org.koin.dsl.module

val useCaseModule = module {

    single<CharactersRemoteDataSource> { RetrofitCharactersDataSource(get<MarvelApi>()) }

    single<CharactersRepository> { CharactersRepositoryImpl(get<CharactersRemoteDataSource>()) }

    single<GetCharactersUseCase>{GetCharactersUseCaseImpl(get<CharactersRepository>())}

    single<GetCategoryUseCase> {
        GetCategoryUseCaseImpl(
            get<CharactersRepository>(),
            get<AppCoroutinesDispatchers>()
        )
    }
}


