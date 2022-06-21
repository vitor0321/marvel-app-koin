package com.example.marvelapp.presentation.fragment.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.model.Character
import com.example.core.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    fun charactersPagingData(query: String): Flow<PagingData<Character>> {
        return getCharactersUseCase
            .invoke(GetCharactersUseCase.GetCharactersParams(query, getPageConfig()))
            .cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig(pageSize = 20)
}