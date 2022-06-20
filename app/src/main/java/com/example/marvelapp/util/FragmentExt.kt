package com.example.marvelapp.util

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.marvelapp.presentation.fragment.characters.CharactersFragmentDirections

fun Fragment.navTo(@IdRes dest: Int) = findNavController().navigate(dest)
fun Fragment.navTo(directions: NavDirections) = findNavController().navigate(directions)
fun Fragment.navTo(@IdRes dest: Int, args: Bundle) = findNavController().navigate(dest, args)
fun Fragment.navBack() = findNavController().navigateUp()

fun Fragment.setSystemStatusBarColorOverColorResource(@ColorRes id: Int) {
    requireActivity().window.statusBarColor = requireActivity().getColor(id)
}

fun Fragment.setSharedElementTransitionOnEnter() {
    TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
        sharedElementEnterTransition = this
    }
}

