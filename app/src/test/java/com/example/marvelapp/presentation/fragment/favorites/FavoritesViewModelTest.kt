package com.example.marvelapp.presentation.fragment.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.usecase.GetFavoritesUseCase
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    @Mock
    private lateinit var favoriteUiStateObserver: Observer<FavoritesViewModel.UiState>

    private val charactersFactory = CharactersFactory()
    private val favoriteListCharacters = listOf(
        charactersFactory.create(CharactersFactory.Hero.ThreeDMan),
        charactersFactory.create(CharactersFactory.Hero.ABomb)
    )

    private val favoriteItemListCharacters = listOf(
        FavoriteItem(
            id = 1011334,
            name = "3-D Man",
            imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
            key = 1011334
        )
    )

    @Mock
    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private lateinit var favoriteViewModel: FavoritesViewModel

    @Before
    fun setUp() {
        favoriteViewModel = FavoritesViewModel(
            getFavoritesUseCase,
            mainCoroutinesRule.testDispatcherProvider
        ).apply {
            state.observeForever(favoriteUiStateObserver)
        }
    }

    @Test
    fun `should notify favorite_uiState with UiState_Empty when return empty list`() =
        runTest {
            //Arrange
            whenever(getFavoritesUseCase.invoke())
                .thenReturn(
                    flowOf(listOf())
                )

            //Act
            favoriteViewModel.getAll()

            //Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoritesViewModel.UiState.ShowEmpty>())
        }

    @Test
    fun `should notify favorite_uiState with UiState_ShowFavorites when return list of favorites`() =
        runTest {
            //Arrange
            whenever(getFavoritesUseCase.invoke())
                .thenReturn(
                    flowOf(favoriteListCharacters)
                )

            //Act
            favoriteViewModel.getAll()

            //Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoritesViewModel.UiState.ShowFavorites>())
            val uiState =
                favoriteViewModel.state.value as FavoritesViewModel.UiState.ShowFavorites
            Assert.assertEquals(favoriteItemListCharacters.first(), uiState.favorites.first())
        }
}