package com.example.marvelapp.presentation.fragment.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.domain.model.ListCategory
import com.example.core.usecase.GetCategoryUseCase
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
    private lateinit var uiStateObserver: Observer<DetailViewModel.UiState>

    private val character = CharactersFactory().create(CharactersFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val event = listOf(EventFactory().create(EventFactory.FakeEvent.FakeComic1))
    private val series = listOf(SeriesFactory().create(SeriesFactory.FakeSeries.FakeSeries1))
    private val stories = listOf(StoriesFactory().create(StoriesFactory.FakeStories.FakeStories1))

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getCategoryUseCase)
        detailViewModel.uiState.observeForever(uiStateObserver)
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
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())

            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
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
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())

            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
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
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())

            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
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
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())

            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
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
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Empty>())
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
            detailViewModel.getCharactersCategories(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Error>())
        }
}