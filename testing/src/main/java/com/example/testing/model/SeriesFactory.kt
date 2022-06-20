package com.example.testing.model

import com.example.core.domain.model.Series

class SeriesFactory {

    fun create(series: FakeSeries) = when (series) {
        FakeSeries.FakeSeries1 -> Series(
            2211506,
            "https://fakeseriesurl.jpg"
        )
    }

    sealed class FakeSeries {
        object FakeSeries1 : FakeSeries()
    }
}