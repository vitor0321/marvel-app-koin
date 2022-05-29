package com.example.marvelapp.presentation.fragment.characters

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.presentation.fragment.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    override fun getViewBinding(): FragmentCharactersBinding =
        FragmentCharactersBinding.inflate(layoutInflater)

    private val viewModel: CharactersViewModel by viewModel()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()
        initObserver()
        observerInitialLoadState()
    }

    private fun initCharactersAdapter() {
        charactersAdapter = CharactersAdapter()
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
                        setShimmerVisibility(true)
                        showToolbar(false)
                        showMenuNavigation(false)
                        FLIPPER_CHILD_LOADING
                    }
                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        showToolbar(true)
                        showMenuNavigation(true)
                        FLIPPER_CHILD_CHARACTER
                    }
                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        showToolbar(false)
                        showMenuNavigation(false)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            charactersAdapter.refresh()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                }
            }
        }
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