package com.example.marvelapp.presentation.fragment.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.model.Character
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.base.CoroutinesDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val coroutineDispatchers: CoroutinesDispatchers
) : ViewModel() {

    var currentSearchQuery = ""

    private val actionCharacters = MutableLiveData<ActionCharacters>()

    val stateCharacters: LiveData<UiStateCharacters> = actionCharacters
        .switchMap { actionCharacters ->
            when (actionCharacters) {
                is ActionCharacters.Search, ActionCharacters.Sort -> {
                    getCharactersUseCase.invoke(
                        GetCharactersUseCase.GetCharactersParams(
                            currentSearchQuery,
                            getPageConfig()
                        )
                    ).cachedIn(viewModelScope).map {
                        UiStateCharacters.SearchResult(it)
                    }.asLiveData(coroutineDispatchers.main())
                }
            }
        }

    fun charactersPagingData(query: String): Flow<PagingData<Character>> {
        return getCharactersUseCase
            .invoke(GetCharactersUseCase.GetCharactersParams(query, getPageConfig()))
            .cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig(pageSize = 20)

    fun searchCharacters() {
        actionCharacters.value = ActionCharacters.Search
    }

    fun applySort() {
        actionCharacters.value = ActionCharacters.Sort
    }

    fun closeSearch() {
        if (currentSearchQuery.isNotEmpty()) {
            currentSearchQuery = ""
        }
    }

    sealed class UiStateCharacters {
        data class SearchResult(val data: PagingData<Character>) : UiStateCharacters()

        sealed class ApplyState : UiStateCharacters() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class ActionCharacters {
        object Search : ActionCharacters()
        object Sort : ActionCharacters()
    }
}