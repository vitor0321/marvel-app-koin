package com.example.marvelapp.framework

import com.example.core.data.repository.storage.StorageLocalDataSource
import com.example.core.data.repository.storage.StorageRepository
import kotlinx.coroutines.flow.Flow

class StorageRepositoryImpl(
    private val storageLocalDataSource: StorageLocalDataSource
): StorageRepository {

    override val sorting: Flow<String>
        get() = storageLocalDataSource.sorting

    override suspend fun saveSorting(sorting: String) {
        storageLocalDataSource.saveSorting(sorting)
    }


    override val intro: Flow<Boolean>
        get() = storageLocalDataSource.intro

    override suspend fun saveIntro(slides: Boolean) {
        storageLocalDataSource.saveIntro(slides)
    }
}