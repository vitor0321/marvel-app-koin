package com.example.marvelapp.framework.di

import com.example.marvelapp.BuildConfig
import org.koin.dsl.module

interface BaseUrl {

    fun baseUrl(): String = BuildConfig.BASE_URL
}

class ProvideBaseUrl : BaseUrl

val baseUrlModule = module {
    single<BaseUrl> { ProvideBaseUrl() }
}
