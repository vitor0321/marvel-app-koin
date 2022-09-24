package com.example.marvelapp.presentation.fragment.detail

import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.CheckFavoriteUseCase
import com.example.core.usecase.RemoveFavoriteUseCase
import com.example.marvelapp.R
import com.example.marvelapp.presentation.common.extensions.watchStatus
import kotlin.coroutines.CoroutineContext

class FavoriteUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) {

    @set:VisibleForTesting
    var currentFavoriteIcon = R.drawable.ic_favorite_unchecked

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutineContext) {
            when (action) {
                is Action.CheckFavorite -> {
                    checkFavoriteUseCase.invoke(
                        CheckFavoriteUseCase.Params(action.characterId)
                    ).watchStatus(
                        loading = { emit(UiState.Loading) },
                        success = { isFavorite ->
                            currentFavoriteIcon = when (isFavorite) {
                                true -> R.drawable.ic_favorite_checked
                                false -> R.drawable.ic_favorite_unchecked
                            }
                            emitFavoriteIcon()
                        },
                        error = {
                            currentFavoriteIcon = R.drawable.ic_favorite_error
                            emitFavoriteErrorIcon()
                        }
                    )
                }
                is Action.AddFavorite -> {
                    action.detailViewArg.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(this.characterId, this.name, this.imageUrl)
                        ).watchStatus(
                            loading = { emit(UiState.Loading) },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_checked
                                emitFavoriteIcon()
                            },
                            error = {
                                currentFavoriteIcon = R.drawable.ic_favorite_error
                                emitFavoriteErrorIcon()
                            }
                        )
                    }
                }
                is Action.RemoveFavorite -> {
                    action.detailViewArg.run {
                        removeFavoriteUseCase.invoke(
                            RemoveFavoriteUseCase.Params(this.characterId, this.name, this.imageUrl)
                        ).watchStatus(
                            loading = { emit(UiState.Loading) },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                                emitFavoriteIcon()
                            },
                            error = {
                                currentFavoriteIcon = R.drawable.ic_favorite_error
                                emitFavoriteErrorIcon()
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun LiveDataScope<UiState>.emitFavoriteIcon() {
        emit(UiState.Icon(currentFavoriteIcon))
    }

    private suspend fun LiveDataScope<UiState>.emitFavoriteErrorIcon() {
        emit(UiState.Error(currentFavoriteIcon))
    }

    fun checkFavorite(characterId: Int) {
        action.value = Action.CheckFavorite(characterId)
    }

    fun update(detailViewArg: DetailViewArg) {
        action.value = when (currentFavoriteIcon) {
            R.drawable.ic_favorite_unchecked -> Action.AddFavorite(detailViewArg)
            R.drawable.ic_favorite_checked -> Action.RemoveFavorite(detailViewArg)
            else -> Action.CheckFavorite(detailViewArg.characterId)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val iconSuccess: Int) : UiState()
        data class Error(@DrawableRes val iconError: Int) : UiState()
    }

    sealed class Action {
        data class CheckFavorite(val characterId: Int) : Action()
        data class AddFavorite(val detailViewArg: DetailViewArg) : Action()
        data class RemoveFavorite(val detailViewArg: DetailViewArg) : Action()
    }
}