package com.example.core.data.repository.characters

import com.example.core.domain.model.CharacterPaging
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.domain.model.Series
import com.example.core.domain.model.Story

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging

    suspend fun fetchComics(characterId: Int): List<Comic>

    suspend fun fetchEvents(characterId: Int): List<Event>

    suspend fun fetchSeries(characterId: Int): List<Series>

    suspend fun fetchStory(characterId: Int): List<Story>
}