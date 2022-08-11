package com.example.marvelapp.presentation.common.extensions

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.core.data.Constants
import com.example.core.data.Constants.DURATION_ANIM

fun Fragment.navTo(@IdRes dest: Int) = findNavController().navigate(dest)
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.navTo(@IdRes dest: Int, args: Bundle) = findNavController().navigate(dest, args)
fun Fragment.navBack() = findNavController().navigateUp()

fun Fragment.setSharedElementTransitionOnEnter() {
    TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
        sharedElementEnterTransition = this
    }
}

fun Fragment.toast(msg: String) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Fragment.startChameleonCorAnim(view: TextView) {
    val chameleonCorAnim: ValueAnimator = ObjectAnimator.ofInt(
        view,
        "textColor",
        -0xfa9b01,
        -0x1000000,
    )
    chameleonCorAnim.duration = DURATION_ANIM
    chameleonCorAnim.setEvaluator(ArgbEvaluator())
    chameleonCorAnim.repeatCount = ValueAnimator.INFINITE
    chameleonCorAnim.repeatMode = ValueAnimator.REVERSE
    chameleonCorAnim.start()
}

fun Fragment.getDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), id)
}

