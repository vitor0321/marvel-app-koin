package com.example.marvelapp.presentation.fragment.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentAboutBinding
import com.example.marvelapp.presentation.common.extensions.startChameleonCorAnim
import com.example.marvelapp.presentation.fragment.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    override fun getViewBinding(): FragmentAboutBinding =
        FragmentAboutBinding.inflate(layoutInflater)
}