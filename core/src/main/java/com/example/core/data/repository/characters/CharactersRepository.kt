package com.example.core.data.repository.characters

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.core.domain.model.Character
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.domain.model.Series
import com.example.core.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCachedCharacters(
        query: String,
        orderBy: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>>

    suspend fun getComics(characterId: Int): List<Comic>

    suspend fun getEvents(characterId: Int): List<Event>

    suspend fun getSeries(characterId: Int): List<Series>

    suspend fun getStory(characterId: Int): List<Story>
}