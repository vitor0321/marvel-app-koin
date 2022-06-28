package com.example.marvelapp.framework.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.Constants.APP_DATABASE_NAME
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.db.dao.FavoriteDao
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> { provideAppDatabase(get<Context>()) }
    single<FavoriteDao> { get<AppDatabase>().favoriteDao() }
}

fun provideAppDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()
