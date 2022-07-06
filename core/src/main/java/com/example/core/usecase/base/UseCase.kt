package com.example.core.usecase.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Caso de uso para usar quando trabalhamos com dados vindo de fora,
 * aonde necessitamos criar um Flow e controlar os estados de Loading, Sucesso e Erro;
 * **/
abstract class UseCase<in P, R> {

    operator fun invoke(params: P): Flow<ResultStatus<R>> = flow {
        emit(ResultStatus.Loading)
        emit(doWork(params))
    }.catch { throwable ->
        emit(ResultStatus.Error(throwable))
    }

    protected abstract suspend fun doWork(params: P): ResultStatus<R>
}

/**
 * Caso de uso para usar quando trabalhamos Paging3;
 * **/
abstract class PagingUseCase<in P, R : Any> {

    operator fun invoke(params: P): Flow<PagingData<R>> = createFlowObservable(params)

    protected abstract fun createFlowObservable(params: P): Flow<PagingData<R>>
}

/**
 * Caso de uso para usar quando trabalhamos com dados internos,
 * como o DataStore do ROOM, porque ele retorna diretamente um Flow;
 * **/
abstract class FlowUseCase<in P, R : Any> {

    suspend operator fun invoke(params: P): Flow<R> = createFlowObservable(params)

    protected abstract suspend fun createFlowObservable(params: P): Flow<R>
}

