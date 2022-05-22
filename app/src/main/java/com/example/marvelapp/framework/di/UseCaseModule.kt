package com.example.marvelapp.framework.di

import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.data.repository.CharactersRepository
import com.example.marvelapp.framework.CharactersRepositoryImpl
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.remote.RetrofitCharactersDataSource
import org.koin.dsl.module

val useCaseModule = module {

    single<CharactersRemoteDataSource<DataWrapperResponse>> {
        RetrofitCharactersDataSource(get<MarvelApi>())
    }

    single<CharactersRepository> {
        CharactersRepositoryImpl(get<CharactersRemoteDataSource<DataWrapperResponse>>())
    }
}


