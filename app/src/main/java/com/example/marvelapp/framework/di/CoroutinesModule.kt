package com.example.marvelapp.framework.di

import com.example.core.usecase.base.AppCoroutinesDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val coroutinesModule = module {
    single { provideDispatchers() }

}

fun provideDispatchers(): AppCoroutinesDispatchers {
    return AppCoroutinesDispatchers(
        Dispatchers.IO,
        Dispatchers.Default,
        Dispatchers.Main
    )
}