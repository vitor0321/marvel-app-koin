package com.example.marvelapp.framework.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.Constants.APP_DATABASE_NAME
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.db.dao.CharacterDao
import com.example.marvelapp.framework.db.dao.FavoriteDao
import com.example.marvelapp.framework.db.dao.RemoteKeyDao
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> { provideAppDatabase(get<Context>()) }
    single<FavoriteDao> { get<AppDatabase>().favoriteDao() }
    single<CharacterDao> { get<AppDatabase>().characterDao() }
    single<RemoteKeyDao> { get<AppDatabase>().remoteKeyDao() }
}

fun provideAppDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()
