package com.example.core.data.repository

interface CharactersRemoteDataSource<T> {

    suspend fun fetchCharacters(quires: Map<String, String>): T
}