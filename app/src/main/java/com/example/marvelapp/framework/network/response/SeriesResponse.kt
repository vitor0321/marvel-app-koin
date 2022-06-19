package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Series
import com.google.gson.annotations.SerializedName

class SeriesResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun SeriesResponse.toSeriesModel(): Series {
    return Series(
        id = this.id,
        imageUrl = this.thumbnail.getHttpUrl()
    )
}