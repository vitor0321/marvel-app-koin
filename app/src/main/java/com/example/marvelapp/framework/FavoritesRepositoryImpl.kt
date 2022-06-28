package com.example.marvelapp.framework

import com.example.core.data.repository.favorites.FavoritesLocalDataSource
import com.example.core.data.repository.favorites.FavoritesRepository
import com.example.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val favoritesLocalDataSource: FavoritesLocalDataSource
) : FavoritesRepository {

    override fun getAll(): Flow<List<Character>> {
        return favoritesLocalDataSource.getAll()
    }

    override suspend fun saveFavorite(character: Character) {
        return favoritesLocalDataSource.save(character)
    }

    override suspend fun deleteFavorite(character: Character) {
        return favoritesLocalDataSource.delete(character)
    }
}