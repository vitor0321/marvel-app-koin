package com.example.testing.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.domain.model.Character

class PagingSourceFactory {

    fun create(heroes: List<Character>) = object : PagingSource<Int, Character>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            return LoadResult.Page(
                data = heroes,
                prevKey = null,
                nextKey = 20
            )
        }

        override fun getRefreshKey(state: PagingState<Int, Character>) = 0
    }
}