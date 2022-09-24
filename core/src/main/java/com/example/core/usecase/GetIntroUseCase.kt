package com.example.core.usecase

import com.example.core.data.repository.storage.StorageRepository
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface GetIntroUseCase {

    suspend operator fun invoke(params: Unit = Unit): Flow<Boolean>
}

class GetIntroCaseImpl(
    private val storageRepository: StorageRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, Boolean>(), GetIntroUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<Boolean> {
        return withContext(dispatchers.io()) {
            storageRepository.intro
        }
    }
}