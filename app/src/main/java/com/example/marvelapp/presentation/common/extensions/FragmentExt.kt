package com.example.marvelapp.presentation.common.util

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navTo(@IdRes dest: Int) = findNavController().navigate(dest)
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.navTo(@IdRes dest: Int, args: Bundle) = findNavController().navigate(dest, args)
fun Fragment.navBack() = findNavController().navigateUp()

fun Fragment.setSharedElementTransitionOnEnter() {
    TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
        sharedElementEnterTransition = this
    }
}

