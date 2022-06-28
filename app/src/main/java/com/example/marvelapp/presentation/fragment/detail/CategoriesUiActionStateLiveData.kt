package com.example.marvelapp.presentation.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.core.usecase.GetCategoryUseCase
import com.example.marvelapp.R
import com.example.marvelapp.util.watchStatus
import kotlin.coroutines.CoroutineContext

class CategoriesUiActionStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val getCategoryUseCase: GetCategoryUseCase,
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutineContext) {
            when (action) {
                is Action.Load -> {
                    getCategoryUseCase.invoke(
                        GetCategoryUseCase.GetCategoriesParams(action.characterId)
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = { data ->
                            val detailParentLis = mutableListOf<DetailParentVE>()

                            val comics = data.listComic
                            val events = data.listEvent
                            val series = data.listSeries
//                            val story = data.listStory

                            if (comics.isNotEmpty()) {
                                comics.map { DetailChildVE(it.id, it.imageUrl) }
                                    .also {
                                        detailParentLis.add(
                                            DetailParentVE(
                                                R.string.details_comics_category,
                                                it
                                            )
                                        )
                                    }
                            }
                            if (events.isNotEmpty()) {
                                events.map { DetailChildVE(it.id, it.imageUrl) }
                                    .also {
                                        detailParentLis.add(
                                            DetailParentVE(R.string.details_events_category, it)
                                        )
                                    }
                            }
                            if (series.isNotEmpty()) {
                                series.map { DetailChildVE(it.id, it.imageUrl) }
                                    .also {
                                        detailParentLis.add(
                                            DetailParentVE(R.string.details_series_category, it)
                                        )
                                    }
                            }
                            if (detailParentLis.isNotEmpty()) {
                                emit(UiState.Success(detailParentLis))
                            } else emit(UiState.Empty)
                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }
            }
        }
    }

    fun load(characterId: Int) {
        action.value = Action.Load(characterId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailParentLis: List<DetailParentVE>) : UiState()
        object Error : UiState()
        object Empty : UiState()
    }

    sealed class Action {
        data class Load(val characterId: Int) : Action()
    }
}