package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Story
import com.google.gson.annotations.SerializedName

class StoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)

fun StoryResponse.toStoryModel(): Story {
    return Story(
        id = id,
        title = title,
        description = description
    )
}