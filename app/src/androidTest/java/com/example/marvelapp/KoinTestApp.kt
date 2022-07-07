package com.example.marvelapp

import android.app.Application
import com.example.marvelapp.di.baseUrlTestModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinTestApp:  Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinTestApp)
            modules(
                baseUrlTestModule
            )
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}