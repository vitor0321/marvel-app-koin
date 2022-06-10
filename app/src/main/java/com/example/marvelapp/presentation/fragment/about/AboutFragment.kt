package com.example.marvelapp.presentation.fragment.about

import com.example.marvelapp.databinding.FragmentAboutBinding
import com.example.marvelapp.presentation.fragment.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    override fun getViewBinding(): FragmentAboutBinding =
        FragmentAboutBinding.inflate(layoutInflater)
}