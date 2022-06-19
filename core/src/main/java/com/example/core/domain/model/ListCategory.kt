package com.example.core.domain.model

data class ListCategory(
    val listComic: List<Comic>,
    val listEvent: List<Event>,
    val listSeries: List<Series>,
    val listStory: List<Story>
)
