package com.example.testing.model

import com.example.core.domain.model.Story

class StoriesFactory {

    fun create(series: FakeStories) = when (series) {
        FakeStories.FakeStories1 -> Story(
            id = 2211506,
            title = "Title",
            description = "Description"
        )
    }

    sealed class FakeStories {
        object FakeStories1 : FakeStories()
    }
}