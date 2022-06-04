package com.example.marvelapp.presentation.fragment.characters

import androidx.paging.PagingData
import com.example.core.usecase.GetCharactersUseCase
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var charactersViewModel: CharactersViewModel

    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val charactersFactory = CharactersFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            charactersFactory.create(CharactersFactory.Hero.ThreeDMan),
            charactersFactory.create(CharactersFactory.Hero.ABomb)
        )
    )

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runBlockingTest {
            whenever(
                getCharactersUseCase.invoke(any())
            ).thenReturn(
                flowOf(pagingDataCharacters)
            )

            val result = charactersViewModel.charactersPagingData("")

            assertEquals(1, result.count())
        }

    @Test(expected = RuntimeException::class)
    fun `should return exception when calling charactersPagingData`() =
        runBlockingTest {
            whenever(
                getCharactersUseCase.invoke(any())
            ).thenThrow(
                RuntimeException()
            )

            charactersViewModel.charactersPagingData("")
        }
}