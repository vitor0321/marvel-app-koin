package com.example.core.usecase

import com.example.core.data.repository.storage.StorageRepository
import com.example.core.usecase.base.CoroutinesDispatchers
import com.example.core.usecase.base.ResultStatus
import com.example.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface SaveIntroUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val intro: Boolean)
}

class SaveIntroUseCaseImpl(
    private val storageRepository: StorageRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveIntroUseCase.Params, Unit>(), SaveIntroUseCase {

    override suspend fun doWork(params: SaveIntroUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            storageRepository.saveIntro(params.intro)
            ResultStatus.Success(Unit)
        }
    }
}