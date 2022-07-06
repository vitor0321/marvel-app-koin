package com.example.core.usecase

import com.example.core.data.mapper.SortingMapperUseCase
import com.example.core.data.repository.storage.StorageRepository
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface GetCharactersSortingUseCase {

    suspend operator fun invoke(params: Unit = Unit): Flow<Pair<String, String>>
}

class GetCharactersSortingUseCaseImpl(
    private val storageRepository: StorageRepository,
    private val sortingMapper: SortingMapperUseCase,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, Pair<String, String>>(), GetCharactersSortingUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<Pair<String, String>> {
        return withContext(dispatchers.io()) {
            storageRepository.sorting.map { sorting ->
                sortingMapper.mapToPair(sorting)
            }
        }
    }
}