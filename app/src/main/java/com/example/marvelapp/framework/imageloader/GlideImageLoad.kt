package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoad : ImageLoader {

    override fun load(
        imageView: ImageView,
        imageUrl: String,
        placeholder: Int,
        fallback: Int
    ) {
        Glide.with(imageView.rootView)
            .load(imageUrl)
            .placeholder(placeholder)
            .fallback(fallback)
            .into(imageView)
    }
}