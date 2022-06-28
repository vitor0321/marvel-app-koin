package com.example.marvelapp

import android.app.Application
import com.example.marvelapp.framework.di.coroutinesModule
import com.example.marvelapp.framework.di.networkModule
import com.example.marvelapp.framework.di.uiModule
import com.example.marvelapp.framework.di.characterRepositoryModule
import com.example.marvelapp.framework.di.databaseModule
import com.example.marvelapp.framework.di.favoritesRepositoryModule
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
                characterRepositoryModule,
                coroutinesModule,
                databaseModule,
                favoritesRepositoryModule,
                networkModule,
                uiModule,
                viewModelModule
            )
        }
    }
}