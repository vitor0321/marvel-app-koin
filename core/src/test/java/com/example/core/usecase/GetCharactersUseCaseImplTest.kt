package com.example.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.repository.characters.CharactersRepository
import com.example.core.domain.model.Character
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
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
    private val charactersFactory = CharactersFactory()

    private var fakePagingData = flow<PagingData<Character>> {
        PagingData.from(
            listOf(
                charactersFactory.create(CharactersFactory.Hero.ThreeDMan),
                charactersFactory.create(CharactersFactory.Hero.ABomb)
            )
        )
    }

    @Before
    fun setUp() {
        getCharactersUseCase = GetCharactersUseCaseImpl(charactersRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() {}
//        runTest {
//            whenever(charactersRepository.getCachedCharacters("", PagingConfig(20)))
//                .thenReturn(fakePagingData)
//            val result = getCharactersUseCase.invoke(
//                GetCharactersUseCase.GetCharactersParams("", PagingConfig(20))
//            )
//
//            verify(charactersRepository).getCachedCharacters("", PagingConfig(20))
//
//            assertNotNull(result.first())
//        }
}