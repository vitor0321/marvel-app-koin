package com.example.marvelapp.presentation.fragment.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.extensions.navTo
import com.example.marvelapp.presentation.fragment.BaseFragment
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersAdapter
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersLoadMoreStateAdapter
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersRefreshStateAdapter
import com.example.marvelapp.presentation.fragment.detail.DetailViewArg
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

    private val imageLoader: ImageLoader by inject()

    private val headerAdapter: CharactersRefreshStateAdapter by lazy {
        CharactersRefreshStateAdapter(
            charactersAdapter::retry
        )
    }

    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter(imageLoader) { character, view ->
            val extras = FragmentNavigatorExtras(
                view to character.name
            )
            val directions = CharactersFragmentDirections
                .actionCharactersFragmentToDetailFragment(
                    character.name,
                    DetailViewArg(character.id, character.name, character.imageUrl)
                )
            findNavController().navigate(directions, extras)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCharactersAdapter()
        loadCharactersAndObserverUiState()
        observerInitialLoadState()
    }

    override fun showActionBarOptionMenu(): Boolean = TRUE

    private fun initCharactersAdapter() {
        postponeEnterTransition()
        binding.recyclerCharacters.run {
            setHasFixedSize(true)
            adapter = charactersAdapter.withLoadStateHeaderAndFooter(
                header = headerAdapter,
                footer = CharactersLoadMoreStateAdapter(
                    charactersAdapter::retry
                )
            )
            viewTreeObserver.addOnDrawListener {
                startPostponedEnterTransition()
                TRUE
            }
        }
    }

    private fun loadCharactersAndObserverUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is CharactersViewModel.UiState.SearchResult -> {
                    charactersAdapter.submitData(viewLifecycleOwner.lifecycle, uiState.data)
                }
            }
        }
        viewModel.searchCharacters()
    }

    private fun observerInitialLoadState() {
        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collectLatest { loadState ->
                headerAdapter.loadState =
                    loadState.mediator
                        ?.refresh
                        ?.takeIf {
                            it is LoadState.Error && charactersAdapter.itemCount > 0
                        } ?: loadState.prepend

                binding.flipperCharacters.displayedChild = when {
                    loadState.mediator?.refresh is LoadState.Loading -> {
                        setUiState(TRUE, FALSE, FALSE, R.color.character_background_status_loading)
                        FLIPPER_CHILD_LOADING
                    }
                    loadState.mediator?.refresh is LoadState.Error
                            && charactersAdapter.itemCount == 0 -> {
                        setUiState(FALSE, FALSE, FALSE, R.color.character_background_status_error)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            charactersAdapter.retry()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading -> {
                        setUiState(FALSE, TRUE, TRUE, R.color.character_background_status)
                        FLIPPER_CHILD_CHARACTER
                    }
                    else -> {
                        setUiState(FALSE, TRUE, TRUE, R.color.character_background_status)
                        FLIPPER_CHILD_CHARACTER
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
        setColorStatusBarAndNavigation(color)
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacter.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.characters_menu_itens, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                navTo(R.id.action_charactersFragment_to_sortFragment)
                true
            }
            R.id.menu_day_night -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_CHARACTER = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
    //    private val dataStore: DataStore<Preferences> by preferencesDataStore(DAY_NIGHT)

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    //        return when (item.itemId) {
    //            R.id.toolbar_day_night -> {
    //                when (FALSE) {
    //                    true -> {
    //                        saveStyleDayNight(DAY_NIGHT, FALSE)
    //                        checkStatusDayNight()
    //                    }
    //                    false -> {
    //                        saveStyleDayNight(DAY_NIGHT, TRUE)
    //                        checkStatusDayNight()
    //                    }
    //                }
    //                true
    //            }
    //            else -> super.onOptionsItemSelected(item)
    //        }
    //    }
    //
    //    private fun checkStatusDayNight() {
    //        when (getStyleDayNight(DAY_NIGHT)) {
    //            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    //            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    //        }
    //    }
    //
    //    private fun saveStyleDayNight(key: String, value: Boolean) = lifecycleScope.launch {
    //        val prefsKey = booleanPreferencesKey(key)
    //        dataStore.edit { dayNight ->
    //            dayNight[prefsKey] = value
    //        }
    //    }
    //
    //    private fun getStyleDayNight(key: String): Boolean {
    //        var resultDataStore = FALSE
    //        lifecycleScope.launch {
    //            val prefsKey = booleanPreferencesKey(key)
    //            val prefs = dataStore.data.first()
    //            resultDataStore = prefs[prefsKey] ?: FALSE
    //        }.isCompleted.apply {
    //            return resultDataStore
    //        }
    //    }
}