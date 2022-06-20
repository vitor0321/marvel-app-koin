package com.example.marvelapp.framework.network

import com.example.marvelapp.framework.network.response.CharacterResponse
import com.example.marvelapp.framework.network.response.ComicResponse
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.EventResponse
import com.example.marvelapp.framework.network.response.SeriesResponse
import com.example.marvelapp.framework.network.response.StoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(
        @QueryMap
        queries: Map<String, String>
    ): DataWrapperResponse<CharacterResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<ComicResponse>

    @GET("characters/{characterId}/events")
    suspend fun getEvents(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<EventResponse>

    @GET("characters/{characterId}/series")
    suspend fun getSeries(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<SeriesResponse>

    @GET("characters/{characterId}/stories")
    suspend fun getStory(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<StoryResponse>
}