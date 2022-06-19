package com.example.marvelapp.framework.remote

import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.CharacterPaging
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.domain.model.Series
import com.example.core.domain.model.Story
import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.network.response.toCharacterModel
import com.example.marvelapp.framework.network.response.toComicModel
import com.example.marvelapp.framework.network.response.toEventModel
import com.example.marvelapp.framework.network.response.toSeriesModel
import com.example.marvelapp.framework.network.response.toStoryModel

class RetrofitCharactersDataSource(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }
        return CharacterPaging(
            data.offset,
            data.total,
            characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        return marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
    }

    override suspend fun fetchEvents(characterId: Int): List<Event> {
        return marvelApi.getEvents(characterId).data.results.map {
            it.toEventModel()
        }
    }

    override suspend fun fetchSeries(characterId: Int): List<Series> {
        return marvelApi.getSeries(characterId).data.results.map {
            it.toSeriesModel()
        }
    }

    override suspend fun fetchStory(characterId: Int): List<Story> {
        return marvelApi.getStory(characterId).data.results.map {
            it.toStoryModel()
        }
    }
}