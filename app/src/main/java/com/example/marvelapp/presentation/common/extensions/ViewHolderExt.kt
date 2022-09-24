package com.example.marvelapp.presentation.common.extensions

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Constants.DURATION_ANIM
import com.example.marvelapp.R

fun RecyclerView.ViewHolder.startChameleonCorAnim(view: TextView) {
    val chameleonCorAnim: ValueAnimator = ObjectAnimator.ofInt(
        view,
        "textColor",
        Math.random().hashCode(),
        Math.random().hashCode()
    )
    chameleonCorAnim.duration = DURATION_ANIM
    chameleonCorAnim.setEvaluator(ArgbEvaluator())
    chameleonCorAnim.repeatCount = ValueAnimator.INFINITE
    chameleonCorAnim.repeatMode = ValueAnimator.REVERSE
    chameleonCorAnim.start()
}

fun RecyclerView.ViewHolder.animationInfinitely(image: ImageView, @ColorRes color: Int) {
    val anim = AnimationUtils.loadAnimation(this.itemView.context, R.anim.zoom_in_out_infinity)
    image.startAnimation(anim)
    val drawable: Drawable = image.drawable
    val filter: ColorFilter = LightingColorFilter(
        this.itemView.resources.getColor(color, null),
        this.itemView.resources.getColor(color, null)
    )
    drawable.colorFilter = filter
}