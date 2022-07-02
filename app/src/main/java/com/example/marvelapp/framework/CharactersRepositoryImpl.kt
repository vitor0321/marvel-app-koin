package com.example.marvelapp.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.core.data.repository.characters.CharactersRemoteDataSource
import com.example.core.data.repository.characters.CharactersRepository
import com.example.core.domain.model.Character
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.domain.model.Series
import com.example.core.domain.model.Story
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.paging.CharactersPagingSource
import com.example.marvelapp.framework.paging.CharactersRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class CharactersRepositoryImpl(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val database: AppDatabase
): CharactersRepository {

//    override fun getCharacters(query: String): PagingSource<Int, Character> {
//        return CharactersPagingSource(remoteDataSource, query)
//    }

    override fun getCachedCharacters(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = CharactersRemoteMediator(query, database, remoteDataSource)
        ) {
            database.characterDao().pagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                Character(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl
                )
            }
        }
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