package com.example.marvelapp.presentation.fragment.detail

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.AddFavoriteUseCase
import com.example.marvelapp.R
import com.example.marvelapp.util.watchStatus
import kotlin.coroutines.CoroutineContext

class FavoriteUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val addFavoriteUseCase: AddFavoriteUseCase,
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutineContext) {
            when (action) {
                Action.Default -> emit(UiState.Icon(R.drawable.ic_favorite_unchecked))
                is Action.Update -> {
                    action.detailViewArg.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(this.characterId, this.name, this.imageUrl)
                        ).watchStatus(
                            loading = { emit(UiState.Loading) },
                            success = { emit(UiState.Icon(R.drawable.ic_favorite_checked)) },
                            error = { emit(UiState.Error(R.drawable.ic_favorite_error)) }
                        )
                    }
                }
            }
        }
    }

    fun setDefault() {
        action.value = Action.Default
    }

    fun update(detailViewArg: DetailViewArg) {
        action.value = Action.Update(detailViewArg)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val iconSuccess: Int) : UiState()
        data class Error(@DrawableRes val iconError: Int) : UiState()
    }

    sealed class Action {
        object Default : Action()
        data class Update(val detailViewArg: DetailViewArg) : Action()
    }
}