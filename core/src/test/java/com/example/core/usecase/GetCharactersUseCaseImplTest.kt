package com.example.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.repository.characters.CharactersRepository
import com.example.core.data.repository.storage.StorageRepository
import com.example.testing.MainCoroutinesRule
import com.example.testing.model.CharactersFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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

    @Mock
    lateinit var storageRepository: StorageRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharactersFactory().create(CharactersFactory.Hero.ThreeDMan)
    private val charactersFactory = CharactersFactory()

    private var fakePagingData = PagingData.from(listOf(hero))

    @Before
    fun setUp() {
        getCharactersUseCase = GetCharactersUseCaseImpl(charactersRepository, storageRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            val pagingConfig = PagingConfig(20)
            val orderBy = "ascending"
            val query = "spider"

            whenever(charactersRepository.getCachedCharacters(query, orderBy, pagingConfig))
                .thenReturn(flowOf(fakePagingData))

            whenever(storageRepository.sorting)
                .thenReturn(flowOf(orderBy))

            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams(query, pagingConfig)
            )

            verify(charactersRepository).getCachedCharacters(query, orderBy, pagingConfig)

            assertNotNull(result.first())
        }
}