package com.example.marvelapp.presentation.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.fragment.BaseFragment
import com.example.marvelapp.util.setSharedElementTransitionOnEnter
import com.example.marvelapp.util.setSystemStatusBarColorOverColorResource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    private val viewModel: DetailViewModel by viewModel()

    private val imageLoader: ImageLoader by inject()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        setViewWithTransition()
        setStyleView()
    }

    private fun initObserve(){
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is DetailViewModel.UiState.Loading -> {
                }
                is DetailViewModel.UiState.Success ->
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentLis, imageLoader)
                    }
                is DetailViewModel.UiState.Error -> {

                }
            }
        }

        viewModel.getComics(args.detailViewArg.characterId)
    }

    private fun setViewWithTransition() {
        val detailViewArgs = args.detailViewArg
        binding.imageCharacter.run {
            imageLoader.load(this, detailViewArgs.imageUrl)
            transitionName = detailViewArgs.name
        }
        setSharedElementTransitionOnEnter()
    }

    private fun setStyleView() {
        showToolbar(TRUE)
        showMenuNavigation(FALSE)
        setSystemStatusBarColorOverColorResource(R.color.black)
    }
}