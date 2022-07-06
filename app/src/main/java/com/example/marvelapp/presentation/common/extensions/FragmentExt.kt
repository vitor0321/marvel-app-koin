package com.example.marvelapp.presentation.common.extensions

import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.Toast
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

fun Fragment.setSharedElementTransitionOnEnter() {
    TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
        sharedElementEnterTransition = this
    }
}

fun Fragment.toast(msg: String) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()


