package com.example.marvelapp

import android.app.Application
import com.example.marvelapp.framework.di.baseUrlModule
import com.example.marvelapp.framework.di.coroutinesModule
import com.example.marvelapp.framework.di.networkModule
import com.example.marvelapp.framework.di.uiModule
import com.example.marvelapp.framework.di.useCaseCharacterModule
import com.example.marvelapp.framework.di.databaseModule
import com.example.marvelapp.framework.di.useCaseDataStore
import com.example.marvelapp.framework.di.useCaseFavoritesModule
import com.example.marvelapp.framework.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MarvelApp : Application( ) {

    override fun onCreate() {
        super.onCreate()
        modulesKoin()
    }

    private fun modulesKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MarvelApp)

            modules(
                baseUrlModule,
                coroutinesModule,
                databaseModule,
                networkModule,
                uiModule,
                useCaseCharacterModule,
                useCaseDataStore,
                useCaseFavoritesModule,
                viewModelModule
            )
        }
    }
}