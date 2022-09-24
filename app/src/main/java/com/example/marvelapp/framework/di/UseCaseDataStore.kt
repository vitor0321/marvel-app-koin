package com.example.marvelapp.framework.di

import android.content.Context
import com.example.core.data.mapper.SortingMapper
import com.example.core.data.mapper.SortingMapperUseCase
import com.example.core.data.repository.storage.StorageLocalDataSource
import com.example.core.data.repository.storage.StorageRepository
import com.example.core.usecase.GetCharactersSortingUseCase
import com.example.core.usecase.GetCharactersSortingUseCaseImpl
import com.example.core.usecase.GetIntroCaseImpl
import com.example.core.usecase.GetIntroUseCase
import com.example.core.usecase.SaveCharactersSortingUseCase
import com.example.core.usecase.SaveCharactersSortingUseCaseImpl
import com.example.core.usecase.SaveIntroUseCase
import com.example.core.usecase.SaveIntroUseCaseImpl
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.marvelapp.framework.StorageRepositoryImpl
import com.example.marvelapp.framework.sourceLocal.DataStorePreferencesDataSource
import org.koin.dsl.module

val useCaseDataStore = module {

    single<StorageRepository> { StorageRepositoryImpl(get<StorageLocalDataSource>()) }

    single<StorageLocalDataSource> { DataStorePreferencesDataSource(get<Context>()) }

    single<SortingMapperUseCase> { SortingMapper() }

    single<GetCharactersSortingUseCase> {
        GetCharactersSortingUseCaseImpl(
            get<StorageRepository>(),
            get<SortingMapperUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    single<SaveCharactersSortingUseCase> {
        SaveCharactersSortingUseCaseImpl(
            get<StorageRepository>(),
            get<SortingMapperUseCase>(),
            get<CoroutinesDispatchers>()
        )
    }

    single<SaveIntroUseCase> {
        SaveIntroUseCaseImpl(
            get<StorageRepository>(),
            get<CoroutinesDispatchers>()
        )
    }

    single<GetIntroUseCase> {
        GetIntroCaseImpl(
            get<StorageRepository>(),
            get<CoroutinesDispatchers>()
        )
    }
}