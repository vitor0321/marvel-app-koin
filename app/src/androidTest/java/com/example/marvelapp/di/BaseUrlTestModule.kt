package com.example.marvelapp.di

import org.koin.dsl.module

interface BaseUrlTest {

    fun baseUrlTest(): String = "http://localhost:8080/"
}

class ProvideBaseUrl : BaseUrlTest

val baseUrlTestModule = module {
    single<BaseUrlTest> { ProvideBaseUrl() }
}
