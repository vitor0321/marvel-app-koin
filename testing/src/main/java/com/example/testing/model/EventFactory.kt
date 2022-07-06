package com.example.testing.model

import com.example.core.domain.model.Event

class EventFactory {

    fun create(event: FakeEvent) = when (event) {
        FakeEvent.FakeEvent1 -> Event(
            2211506,
            "https://fakeeventurl.jpg"
        )
    }

    sealed class FakeEvent {
        object FakeEvent1 : FakeEvent()
    }
}