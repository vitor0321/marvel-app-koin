package com.example.marvelapp.presentation.fragment.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.GetFavoritesUseCase
import com.example.core.usecase.base.CoroutinesDispatchers
import kotlinx.coroutines.flow.catch

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val coroutineDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutineDispatchers.main()) {
            when (action) {
                is Action.GetAll -> {
                    getFavoritesUseCase.invoke()
                        .catch {
                            emit(UiState.ShowEmpty)
                        }
                        .collect {
                            val items = it.map { character ->
                                FavoriteItem(
                                    character.id,
                                    character.name,
                                    character.imageUrl
                                )
                            }
                            val uiState = if (items.isEmpty()) {
                                UiState.ShowEmpty
                            } else UiState.ShowFavorites(items)
                            emit(uiState)
                        }
                }
            }
        }
    }

    fun getAll() {
        action.value = Action.GetAll
    }

    sealed class UiState {
        data class ShowFavorites(val favorites: List<FavoriteItem>) : UiState()
        object ShowEmpty : UiState()
    }

    sealed class Action {
        object GetAll : Action()
    }
}