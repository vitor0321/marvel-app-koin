package com.example.marvelapp.framework.di

import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.CoroutinesDispatchers
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutinesDispatchers> { provideDispatchers() }

}

fun provideDispatchers(): CoroutinesDispatchers = AppCoroutinesDispatchers()