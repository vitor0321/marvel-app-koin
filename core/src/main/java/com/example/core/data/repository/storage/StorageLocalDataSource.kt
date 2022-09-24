package com.example.core.data.repository.storage

import kotlinx.coroutines.flow.Flow

interface StorageLocalDataSource {

    val sorting: Flow<String>

    suspend fun saveSorting(sorting: String)

    val intro: Flow<Boolean>

    suspend fun saveIntro(slides: Boolean)
}