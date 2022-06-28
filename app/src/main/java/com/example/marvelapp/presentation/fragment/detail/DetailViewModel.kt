package com.example.marvelapp.presentation.fragment.detail

import androidx.lifecycle.ViewModel
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.base.CoroutinesDispatchers

class DetailViewModel(
    getCategoryUseCase: GetCategoryUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    coroutineDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = CategoriesUiActionStateLiveData(
        coroutineDispatchers.main(),
        getCategoryUseCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutineDispatchers.main(),
        addFavoriteUseCase
    )

    init {
        favorite.setDefault()
    }
}