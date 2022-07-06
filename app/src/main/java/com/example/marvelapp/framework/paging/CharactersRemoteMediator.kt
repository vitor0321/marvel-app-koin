package com.example.marvelapp.framework.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.core.data.repository.characters.CharactersRemoteDataSource
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.db.entity.CharacterEntity
import com.example.marvelapp.framework.db.entity.RemoteKeyEntity
import okio.IOException
import retrofit2.HttpException

/**
 * O Mediator vai fazer a chamada a API e salvar em um banco de dados local,
 * e ao mesmo tempo mostrar os resultados. Com isso os dados são salvos localmente podendo
 * usar o APP em modo Offline, e o Mediator cuida de fazer essa mediação entre a fonte de dados
 * local e a remota.
 * **/
@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val query: String,
    private val database: AppDatabase,
    private val remoteDataSource: CharactersRemoteDataSource
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao = database.characterDao()
    private val remoteKeyDao = database.remoteKeyDao()

    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKey()
                    }
                    if (remoteKey.nextOffset == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextOffset
                }
            }

            val queries = hashMapOf(
                "offset" to offset.toString()
            )
            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }
            val characterPaging = remoteDataSource.fetchCharacters(queries)
            val responseOffset = characterPaging.offset
            val totalCharacters = characterPaging.total

            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    remoteKeyDao.clearAll()
                    characterDao.clearAll()
                }
                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(nextOffset = responseOffset + state.config.pageSize)
                )
                val charactersEntities = characterPaging.characters.map {
                    CharacterEntity(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrl
                    )
                }
                characterDao.insertAll(charactersEntities)
            }
            MediatorResult.Success(endOfPaginationReached = responseOffset >= totalCharacters)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}