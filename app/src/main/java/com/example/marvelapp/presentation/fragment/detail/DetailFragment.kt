package com.example.marvelapp.presentation.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.fragment.BaseFragment
import com.example.marvelapp.presentation.common.util.setSharedElementTransitionOnEnter
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

        loadCategoriesAndObserverUiState()
        setAndObserverFavoriteUiState()
        setViewWithTransition()
        setStyleView()
    }

    override fun showActionBarOptionMenu(): Boolean = FALSE

    private fun loadCategoriesAndObserverUiState() {
        viewModel.categories.load(args.detailViewArg.characterId)
        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->
            binding.apply {
                flipperDetail.displayedChild = when (uiState) {
                    CategoriesUiActionStateLiveData.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                    is CategoriesUiActionStateLiveData.UiState.Success ->
                        recyclerParentDetail.run {
                            setHasFixedSize(true)
                            adapter = DetailParentAdapter(uiState.detailParentLis, imageLoader)
                            FLIPPER_CHILD_POSITION_DETAIL
                        }
                    CategoriesUiActionStateLiveData.UiState.Error -> {
                        includeErrorView.buttonRetry.setOnClickListener {
                            viewModel.categories.load(args.detailViewArg.characterId)
                        }
                        FLIPPER_CHILD_POSITION_ERROR
                    }
                    CategoriesUiActionStateLiveData.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
                }
            }
        }
    }

    private fun setAndObserverFavoriteUiState() {
        viewModel.favorite.checkFavorite(args.detailViewArg.characterId)
        binding.imageFavoriteIcon.setOnClickListener {
            viewModel.favorite.update(args.detailViewArg)
        }
        viewModel.favorite.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperFavorite.displayedChild = when (uiState) {
                FavoriteUiActionStateLiveData.UiState.Loading ->
                    FLIPPER_CHILD_POSITION_FAVORITE_LOADING
                is FavoriteUiActionStateLiveData.UiState.Icon -> {
                    binding.imageFavoriteIcon.setImageResource(uiState.iconSuccess)
                    FLIPPER_CHILD_POSITION_FAVORITE_SUCCESS
                }
                is FavoriteUiActionStateLiveData.UiState.Error -> {
                    binding.imageFavoriteIcon.setImageResource(uiState.iconError)
                    FLIPPER_CHILD_POSITION_FAVORITE_ERROR
                }
            }
        }
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
        setColorStatusBarAndNavigation(R.color.detail_background_status)
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3

        private const val FLIPPER_CHILD_POSITION_FAVORITE_SUCCESS = 0
        private const val FLIPPER_CHILD_POSITION_FAVORITE_LOADING = 1
        private const val FLIPPER_CHILD_POSITION_FAVORITE_ERROR = 0
    }
}