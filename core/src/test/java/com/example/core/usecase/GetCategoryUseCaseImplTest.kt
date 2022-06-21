package com.example.core.usecase

import com.example.core.data.repository.CharactersRepository
import com.example.core.usecase.base.ResultStatus
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.example.testing.model.SeriesFactory
import com.example.testing.model.StoriesFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCategoryUseCaseImplTest {

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    @Mock
    private lateinit var repository: CharactersRepository

    private val character = CharactersFactory().create(CharactersFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val event = listOf(EventFactory().create(EventFactory.FakeEvent.FakeComic1))
    private val series = listOf(SeriesFactory().create(SeriesFactory.FakeSeries.FakeSeries1))
    private val stories = listOf(StoriesFactory().create(StoriesFactory.FakeStories.FakeStories1))

    private lateinit var getCategoryUseCase: GetCategoryUseCase

    @Before
    fun setUp() {
        getCategoryUseCase =
            GetCategoryUseCaseImpl(repository, mainCoroutinesRule.testDispatcherProvider)
    }

    @Test
    fun `should return Success from ResultStatus when get all requests return success`() =
        runTest {
            //Arrange
            whenever(repository.getComics(character.id)).thenReturn(comics)
            whenever(repository.getEvents(character.id)).thenReturn(event)
            whenever(repository.getSeries(character.id)).thenReturn(series)
            whenever(repository.getStory(character.id)).thenReturn(stories)

            //Act
            val result =
                getCategoryUseCase.invoke(GetCategoryUseCase.GetCategoriesParams(character.id))

            //Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStatus when get comics requests return error`() =
        runTest {
            //Arrange
            whenever(repository.getComics(character.id)).thenAnswer { throw  Throwable() }

            //Act
            val result =
                getCategoryUseCase.invoke(GetCategoryUseCase.GetCategoriesParams(character.id))

            //Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }

    @Test
    fun `should return Error from ResultStatus when get event requests return error`() =
        runTest {
            //Arrange
            whenever(repository.getComics(character.id)).thenReturn(comics)
            whenever(repository.getEvents(character.id)).thenAnswer { throw  Throwable() }

            //Act
            val result =
                getCategoryUseCase.invoke(GetCategoryUseCase.GetCategoriesParams(character.id))

            //Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }

    @Test
    fun `should return Error from ResultStatus when get series requests return error`() =
        runTest {
            //Arrange
            whenever(repository.getComics(character.id)).thenReturn(comics)
            whenever(repository.getEvents(character.id)).thenReturn(event)
            whenever(repository.getSeries(character.id)).thenAnswer { throw  Throwable() }

            //Act
            val result =
                getCategoryUseCase.invoke(GetCategoryUseCase.GetCategoriesParams(character.id))

            //Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }

    @Test
    fun `should return Error from ResultStatus when get story requests return error`() =
        runTest {
            //Arrange
            whenever(repository.getComics(character.id)).thenReturn(comics)
            whenever(repository.getEvents(character.id)).thenReturn(event)
            whenever(repository.getSeries(character.id)).thenReturn(series)
            whenever(repository.getStory(character.id)).thenAnswer { throw  Throwable() }

            //Act
            val result =
                getCategoryUseCase.invoke(GetCategoryUseCase.GetCategoriesParams(character.id))

            //Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            assertTrue(resultList[1] is ResultStatus.Error)
        }
}