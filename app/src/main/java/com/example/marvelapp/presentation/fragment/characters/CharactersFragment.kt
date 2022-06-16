package com.example.marvelapp.presentation.fragment.characters

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.fragment.BaseFragment
import com.example.marvelapp.presentation.fragment.detail.DetailViewArg
import com.example.marvelapp.util.setSystemStatusBarColorOverColorResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)

    private val viewModel: CharactersViewModel by viewModel()
    private lateinit var charactersAdapter: CharactersAdapter
    private val imageLoader: ImageLoader by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()
        initObserver()
        observerInitialLoadState()
    }

    private fun initCharactersAdapter() {
        charactersAdapter = CharactersAdapter(imageLoader) { character, view ->
            val extras = FragmentNavigatorExtras(
                view to character.name
            )
            val directions = CharactersFragmentDirections
                .actionCharactersFragmentToDetailFragment(
                    character.name,
                    DetailViewArg(character.id,character.name, character.imageUrl)
                )
            findNavController().navigate(directions, extras)
        }
        binding.recyclerCharacters.run {
            scrollToPosition(0)
            setHasFixedSize(true)
            adapter = charactersAdapter.withLoadStateFooter(
                footer = CharactersLoadStateAdapter(
                    charactersAdapter::retry
                )
            )
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersPagingData("").collect { pagingData ->
                    charactersAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun observerInitialLoadState() {
        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collectLatest { loadState ->
                binding.flipperCharacters.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        setUiState(TRUE, FALSE, FALSE, R.color.white)
                        FLIPPER_CHILD_LOADING
                    }
                    is LoadState.NotLoading -> {
                        setUiState(FALSE, TRUE, TRUE, R.color.purple_500)
                        FLIPPER_CHILD_CHARACTER
                    }
                    is LoadState.Error -> {
                        setUiState(FALSE, FALSE, FALSE, R.color.black_700)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            charactersAdapter.refresh()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                }
            }
        }
    }

    private fun setUiState(
        shimmer: Boolean,
        toolbar: Boolean,
        menuNav: Boolean,
        @ColorRes color: Int
    ) {
        setShimmerVisibility(shimmer)
        showToolbar(toolbar)
        showMenuNavigation(menuNav)
        setSystemStatusBarColorOverColorResource(color)
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacter.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTER = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}