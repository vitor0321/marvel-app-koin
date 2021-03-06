package com.example.marvelapp.presentation.fragment.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.domain.model.ListCategory
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.CheckFavoriteUseCase
import com.example.core.usecase.GetCategoryUseCase
import com.example.core.usecase.RemoveFavoriteUseCase
import com.example.core.usecase.base.ResultStatus
import com.example.marvelapp.R
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.example.testing.model.SeriesFactory
import com.example.testing.model.StoriesFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    @Mock
    private lateinit var getCategoryUseCase: GetCategoryUseCase

    @Mock
    private lateinit var checkFavoriteUseCase: CheckFavoriteUseCase

    @Mock
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    private lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    @Mock
    private lateinit var categoriesUiStateObserver: Observer<CategoriesUiActionStateLiveData.UiState>

    @Mock
    private lateinit var favoriteUiStateObserver: Observer<FavoriteUiActionStateLiveData.UiState>

    private val character = CharactersFactory().create(CharactersFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val event = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))
    private val series = listOf(SeriesFactory().create(SeriesFactory.FakeSeries.FakeSeries1))
    private val stories = listOf(StoriesFactory().create(StoriesFactory.FakeStories.FakeStories1))

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(
            getCategoryUseCase,
            checkFavoriteUseCase,
            addFavoriteUseCase,
            removeFavoriteUseCase,
            mainCoroutinesRule.testDispatcherProvider
        ).apply {
            categories.state.observeForever(categoriesUiStateObserver)
            favorite.state.observeForever(favoriteUiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() =
        runTest {
            //Arrange
            whenever(getCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(ListCategory(comics, event, series, stories)))
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(categoriesUiStateObserver).onChanged(isA<CategoriesUiActionStateLiveData.UiState.Success>())

            val uiStateSuccess =
                detailViewModel.categories.state.value as CategoriesUiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentLis

            assertEquals(3, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
            assertEquals(
                R.string.details_events_category,
                categoriesParentList[1].categoryStringResId
            )
            assertEquals(
                R.string.details_series_category,
                categoriesParentList[2].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() =
        runTest {
            //Arrange
            whenever(getCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            ListCategory(comics, emptyList(), emptyList(), emptyList())
                        )
                    )
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(categoriesUiStateObserver).onChanged(isA<CategoriesUiActionStateLiveData.UiState.Success>())

            val uiStateSuccess =
                detailViewModel.categories.state.value as CategoriesUiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentLis
            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() =
        runTest {
            //Arrange
            whenever(getCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            ListCategory(emptyList(), event, emptyList(), emptyList())
                        )
                    )
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(categoriesUiStateObserver).onChanged(isA<CategoriesUiActionStateLiveData.UiState.Success>())

            val uiStateSuccess =
                detailViewModel.categories.state.value as CategoriesUiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentLis
            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_events_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only series`() =
        runTest {
            //Arrange
            whenever(getCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            ListCategory(emptyList(), emptyList(), series, emptyList())
                        )
                    )
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(categoriesUiStateObserver).onChanged(isA<CategoriesUiActionStateLiveData.UiState.Success>())

            val uiStateSuccess =
                detailViewModel.categories.state.value as CategoriesUiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentLis
            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_series_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() =
        runTest {
            //Arrange
            whenever(getCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            ListCategory(emptyList(), emptyList(), emptyList(), emptyList())
                        )
                    )
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(categoriesUiStateObserver).onChanged(isA<CategoriesUiActionStateLiveData.UiState.Empty>())
        }

    @Test
    fun `should notify uiState with Error from UiState when get character categories returns an exception`() =
        runTest {
            //Arrange
            whenever(getCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Error(Throwable()))
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(categoriesUiStateObserver).onChanged(isA<CategoriesUiActionStateLiveData.UiState.Error>())
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when check favorite returns true`() =
        runTest {
            //Arrange
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(true))
                )

            //Act
            detailViewModel.favorite.checkFavorite(character.id)

            //Assert
            verify(favoriteUiStateObserver)
                .onChanged(isA<FavoriteUiActionStateLiveData.UiState.Icon>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_checked, uiState.iconSuccess)
        }

    @Test
    fun `should notify favorite_uiState with not filled favorite icon when check favorite returns true`() =
        runTest {
            //Arrange
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(false))
                )

            //Act
            detailViewModel.favorite.checkFavorite(character.id)

            //Assert
            verify(favoriteUiStateObserver)
                .onChanged(isA<FavoriteUiActionStateLiveData.UiState.Icon>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_unchecked, uiState.iconSuccess)
        }

    @Test
    fun `should notify favorite_uiState with error filled favorite icon when check favorite returns true`() =
        runTest {
            //Arrange
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Error(Throwable()))
                )

            //Act
            detailViewModel.favorite.checkFavorite(character.id)

            //Assert
            verify(favoriteUiStateObserver)
                .onChanged(isA<FavoriteUiActionStateLiveData.UiState.Error>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Error
            assertEquals(R.drawable.ic_favorite_error, uiState.iconError)
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when current icon is unchecked`() =
        runTest {
            //Arrange
            whenever(addFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(Unit))
                )

            //Act
            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                favorite.update(
                    DetailViewArg(character.id, character.name, character.imageUrl)
                )
            }

            //Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLiveData.UiState.Icon>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_checked, uiState.iconSuccess)
        }

    @Test
    fun `should notify favorite_uiState with error icon when try change favorite`() =
        runTest {
            //Arrange
            whenever(addFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Error(Throwable()))
                )

            //Act
            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                favorite.update(
                    DetailViewArg(character.id, character.name, character.imageUrl)
                )
            }

            //Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLiveData.UiState.Error>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Error
            assertEquals(R.drawable.ic_favorite_error, uiState.iconError)
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when try remove check favorite`() =
        runTest {
            //Arrange
            whenever(removeFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(Unit))
                )

            //Act
            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_checked
                favorite.update(
                    DetailViewArg(character.id, character.name, character.imageUrl)
                )
            }

            //Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLiveData.UiState.Icon>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_unchecked, uiState.iconSuccess)
        }

    @Test
    fun `should notify favorite_uiState with error icon when try remove favorite`() =
        runTest {
            //Arrange
            whenever(removeFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Error(Throwable()))
                )

            //Act
            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_checked
                favorite.update(
                    DetailViewArg(character.id, character.name, character.imageUrl)
                )
            }

            //Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLiveData.UiState.Error>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLiveData.UiState.Error
            assertEquals(R.drawable.ic_favorite_error, uiState.iconError)
        }

}