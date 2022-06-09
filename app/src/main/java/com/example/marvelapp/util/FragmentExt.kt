package com.example.marvelapp.util

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

fun Fragment.setSystemStatusBarColorOverColorResource(@ColorRes id: Int) {
    requireActivity().window.statusBarColor = requireActivity().getColor(id)
}

