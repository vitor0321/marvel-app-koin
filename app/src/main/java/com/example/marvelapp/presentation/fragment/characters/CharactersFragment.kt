package com.example.marvelapp.presentation.fragment.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.core.data.Constants.MENU_DARK_LIGHT_FIREBASE
import com.example.core.data.Constants.MENU_SORTING_FIREBASE
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.extensions.hideSystemBars
import com.example.marvelapp.presentation.common.extensions.navTo
import com.example.marvelapp.presentation.common.extensions.showSystemBars
import com.example.marvelapp.presentation.fragment.BaseFragment
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersAdapter
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersLoadMoreStateAdapter
import com.example.marvelapp.presentation.fragment.characters.adapters.CharactersRefreshStateAdapter
import com.example.marvelapp.presentation.fragment.detail.DetailViewArg
import com.example.marvelapp.presentation.sort.SortFragment.Companion.SORTING_APPLIED_BASK_STACK_KEY
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
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

    private val remoteConfig: FirebaseRemoteConfig by lazy { Firebase.remoteConfig }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.crashlytics.log("CharacterFragment - onViewCreated")
        initCharactersAdapter()
        loadCharactersAndObserverUiState()
        observerInitialLoadState()
        observerSortingData()
    }

    override fun showActionBarOptionMenu(): Boolean = TRUE

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Firebase.crashlytics.log("CharacterFragment - onCreateOptionsMenu")
        inflater.inflate(R.menu.characters_menu_itens, menu)
        menu.findItem(R.id.menu_sort).isVisible = FALSE
        menu.findItem(R.id.menu_day_night).isVisible = FALSE

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    when (remoteConfig.getBoolean(MENU_SORTING_FIREBASE)) {
                        true -> menu.findItem(R.id.menu_sort).isVisible = TRUE
                        false -> menu.findItem(R.id.menu_sort).isVisible = FALSE
                    }
                    when (remoteConfig.getBoolean(MENU_DARK_LIGHT_FIREBASE)) {
                        true -> menu.findItem(R.id.menu_day_night).isVisible = TRUE
                        false -> menu.findItem(R.id.menu_day_night).isVisible = FALSE

                    }
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Firebase.crashlytics.log("CharacterFragment - onOptionsItemSelected")
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

    private fun initCharactersAdapter() {
        Firebase.crashlytics.log("CharacterFragment - initCharactersAdapter")
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
                        requireActivity().hideSystemBars()
                        FLIPPER_CHILD_LOADING
                    }
                    loadState.mediator?.refresh is LoadState.Error
                            && charactersAdapter.itemCount == 0 -> {
                        setUiState(FALSE, FALSE, FALSE, R.color.character_background_status_error)
                        binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                            charactersAdapter.retry()
                        }
                        requireActivity().hideSystemBars()
                        FLIPPER_CHILD_ERROR
                    }
                    loadState.source.refresh is LoadState.NotLoading
                            || loadState.mediator?.refresh is LoadState.NotLoading -> {
                        setUiState(FALSE, TRUE, TRUE, R.color.character_background_status)
                        requireActivity().showSystemBars()
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

    private fun observerSortingData() {
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.charactersFragment)
        val observer = LifecycleEventObserver { _, event ->
            val isSortingApplied =
                navBackStackEntry.savedStateHandle.contains(SORTING_APPLIED_BASK_STACK_KEY)
            if (event == Lifecycle.Event.ON_RESUME && isSortingApplied) {
                viewModel.applySort()
                navBackStackEntry.savedStateHandle.remove<Boolean>(SORTING_APPLIED_BASK_STACK_KEY)
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
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