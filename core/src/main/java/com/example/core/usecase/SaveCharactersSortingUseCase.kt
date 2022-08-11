package com.example.core.usecase

import com.example.core.data.mapper.SortingMapperUseCase
import com.example.core.data.repository.storage.StorageRepository
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.core.usecase.base.ResultStatus
import com.example.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface SaveCharactersSortingUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val sortingPair: Pair<String, String>)
}

class SaveCharactersSortingUseCaseImpl(
    private val storageRepository: StorageRepository,
    private val sortingMapper: SortingMapperUseCase,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveCharactersSortingUseCase.Params, Unit>(), SaveCharactersSortingUseCase {

    override suspend fun doWork(params: SaveCharactersSortingUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            storageRepository.saveSorting(sortingMapper.mapFromPair(params.sortingPair))
            ResultStatus.Success(Unit)
        }
    }
}