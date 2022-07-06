package com.example.marvelapp.presentation.activity

import androidx.annotation.ColorRes

interface ActivityCallback {

    fun showMenuNavigation(show: Boolean)
    fun showToolbar(show: Boolean)
    fun setColorStatusBarAndNavigation(@ColorRes color: Int)
}