package com.example.core.usecase

import com.example.core.data.repository.CharactersRepository
import com.example.core.domain.model.ListCategory
import com.example.core.usecase.base.AppCoroutinesDispatchers
import com.example.core.usecase.base.ResultStatus
import com.example.core.usecase.base.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface GetCategoryUseCase {

    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<ListCategory>>

    data class GetComicsParams(val characterId: Int)
}

class GetCategoryUseCaseImpl(
    private val repository: CharactersRepository,
    private val dispatchers: AppCoroutinesDispatchers
) : GetCategoryUseCase, UseCase<GetCategoryUseCase.GetComicsParams, ListCategory>() {

    override suspend fun doWork(
        params: GetCategoryUseCase.GetComicsParams
    ): ResultStatus<ListCategory> {
        return withContext(dispatchers.io) {
            val comicsDeferred = async { repository.getComics(params.characterId) }
            val eventsDeferred = async { repository.getEvents(params.characterId) }
            val seriesDeferred = async { repository.getSeries(params.characterId) }
            val storyDeferred = async { repository.getStory(params.characterId) }
            val comics = comicsDeferred.await()
            val event = eventsDeferred.await()
            val series = seriesDeferred.await()
            val story = storyDeferred.await()

            ResultStatus.Success(
                ListCategory(
                    listComic = comics,
                    listEvent = event,
                    listSeries = series,
                    listStory = story
                )
            )
        }
    }
}