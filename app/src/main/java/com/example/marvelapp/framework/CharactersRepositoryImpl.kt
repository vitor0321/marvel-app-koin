package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.data.repository.CharactersRepository
import com.example.core.domain.model.Character
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.domain.model.Series
import com.example.core.domain.model.Story
import com.example.marvelapp.framework.paging.CharactersPagingSource

class CharactersRepositoryImpl(
    private val remoteDataSource: CharactersRemoteDataSource
): CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }

    override suspend fun getSeries(characterId: Int): List<Series> {
        return remoteDataSource.fetchSeries(characterId)
    }

    override suspend fun getStory(characterId: Int): List<Story> {
        return remoteDataSource.fetchStory(characterId)
    }
}