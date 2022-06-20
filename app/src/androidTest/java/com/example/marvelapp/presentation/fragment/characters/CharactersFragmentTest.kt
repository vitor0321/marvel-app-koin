package com.example.marvelapp.presentation.fragment.characters

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.marvelapp.createRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4ClassRunner::class)
class CharactersFragmentTest {

    private val fragmentViewModel: CharactersViewModel = mockk(relaxed = true)
    private val fragment = CharactersFragment()

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        single {
            fragmentViewModel
        }
    })

    @Test
    fun shouldShowCharacters_whenViewIsCreated() {

    }
}