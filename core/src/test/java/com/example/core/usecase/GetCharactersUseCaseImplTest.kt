package com.example.core.usecase

import androidx.paging.PagingConfig
import com.example.core.data.repository.CharactersRepository
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.example.testing.pagingSource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    @Mock
    lateinit var charactersRepository: CharactersRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharactersFactory().create(CharactersFactory.Hero.ThreeDMan)

    private var fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp() {
        getCharactersUseCase = GetCharactersUseCaseImpl(charactersRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runBlockingTest {
            whenever(charactersRepository.getCharacters(""))
                .thenReturn(fakePagingSource)
            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams("", PagingConfig(20))
            )

            verify(charactersRepository).getCharacters("")

            assertNotNull(result.first())
        }
}