package com.example.marvelapp.presentation.fragment.favorites

import android.os.Bundle
import android.view.View
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentFavoritesBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.getGenericAdapterOf
import com.example.marvelapp.presentation.fragment.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Boolean.TRUE

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override fun getViewBinding(): FragmentFavoritesBinding =
        FragmentFavoritesBinding.inflate(layoutInflater)

    private val viewModel: FavoritesViewModel by viewModel()

    private val imageLoader: ImageLoader by inject()

    private val favoriteAdapter by lazy {
        getGenericAdapterOf {
            FavoritesViewHolder.create(it, imageLoader)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFavoriteAdapter()
        setUiState()
        initObserverFavorites()
    }

    private fun initFavoriteAdapter() {
        binding.recyclerFavorites.run {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun initObserverFavorites() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.viewFlipperFavorites.displayedChild = when (uiState) {
                is FavoritesViewModel.UiState.ShowFavorites -> {
                    favoriteAdapter.submitList(uiState.favorites)
                    FLIPPER_CHILD_POSITION_CHARACTERS
                }
                FavoritesViewModel.UiState.ShowEmpty -> {
                    favoriteAdapter.submitList(emptyList())
                    FLIPPER_CHILD_POSITION_EMPTY
                }
            }
        }
        viewModel.getAll()
    }

    override fun showActionBarOptionMenu(): Boolean = TRUE

    private fun setUiState() {
        showToolbar(TRUE)
        showMenuNavigation(TRUE)
        setColorStatusBarAndNavigation(R.color.character_background_status)
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_CHARACTERS = 0
        private const val FLIPPER_CHILD_POSITION_EMPTY = 1
    }
}