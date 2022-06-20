package com.example.marvelapp.presentation.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ListCategory
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.base.ResultStatus
import com.example.marvelapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getCharactersCategories(characterId: Int) = viewModelScope.launch {
        getCategoryUseCase(GetCategoryUseCase.GetCategoriesParams(characterId)).watchStatus()
    }

    private fun Flow<ResultStatus<ListCategory>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            _uiState.value = when (status) {
                is ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> {
                    val detailParentLis = mutableListOf<DetailParentVE>()

                    val comics = status.data.listComic
                    val events = status.data.listEvent
                    val series = status.data.listSeries
//                    val story = status.data.listStory

                    if (comics.isNotEmpty()) {
                        comics.map { DetailChildVE(it.id, it.imageUrl) }
                            .also {
                                detailParentLis.add(DetailParentVE(R.string.details_comics_category, it))
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
                        UiState.Success(detailParentLis)
                    } else UiState.Empty
                }
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailParentLis: List<DetailParentVE>) : UiState()
        object Error : UiState()
        object Empty : UiState()
    }
}