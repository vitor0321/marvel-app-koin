package com.example.marvelapp.presentation.fragment.characters

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.marvelapp.R
import com.example.marvelapp.createRule
import com.example.marvelapp.di.baseUrlTestModule
import com.example.marvelapp.extension.asJsonString
import com.example.marvelapp.framework.di.BaseUrl
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4ClassRunner::class)
class CharactersFragmentTest {

    private val fragmentViewModel: CharactersViewModel = mockk(relaxed = true)
    private val fragment = CharactersFragment()

    private lateinit var server: MockWebServer

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        single { fragmentViewModel }
    })

    @Before
    fun setUp() {
        server = MockWebServer().apply {
            start(8080)
        }
        launchFragmentInContainer<CharactersFragment>()
    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    @Test
    fun shouldShowCharacters_whenViewIsCreated() {
        server.enqueue(MockResponse().setBody("characters_p1.json".asJsonString()))
        onView(
            withId(R.id.recycler_characters)
        ).check(
            matches(isDisplayed())
        )
    }
}