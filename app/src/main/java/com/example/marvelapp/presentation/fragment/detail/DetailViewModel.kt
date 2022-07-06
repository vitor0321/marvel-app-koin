package com.example.marvelapp.presentation.fragment.detail

import androidx.lifecycle.ViewModel
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.CheckFavoriteUseCase
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.RemoveFavoriteUseCase
import com.example.core.usecase.base.CoroutinesDispatchers

class DetailViewModel(
    getCategoryUseCase: GetCategoryUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    removeFavoriteUseCase: RemoveFavoriteUseCase,
    coroutineDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    val categories = CategoriesUiActionStateLiveData(
        coroutineDispatchers.main(),
        getCategoryUseCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutineDispatchers.main(),
        checkFavoriteUseCase,
        addFavoriteUseCase,
        removeFavoriteUseCase
    )
}