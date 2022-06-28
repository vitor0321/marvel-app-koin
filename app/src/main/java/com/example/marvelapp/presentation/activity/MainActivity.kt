package com.example.marvelapp.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.presentation.common.extensions.viewBinding

class MainActivity : AppCompatActivity(), ActivityCallback {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setSupportActionBar(binding.toolbarApp)
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavMain.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.charactersFragment, R.id.favoritesFragment, R.id.aboutFragment)
        )

        binding.toolbarApp.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestination) {
                binding.toolbarApp.setNavigationIcon(R.drawable.ic_back)
            }
        }
    }

    override fun showMenuNavigation(show: Boolean) {
        binding.bottomNavMain.isVisible = show
    }

    override fun showToolbar(show: Boolean) {
        binding.toolbarApp.isVisible = show
    }

    override fun setColorStatusBarAndNavigation(color: Int) {
        window.statusBarColor = this.getColor(color)
        window.navigationBarColor = this.getColor(color)

        val statusNightOrDay = isDarkMode()
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = statusNightOrDay
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightNavigationBars = statusNightOrDay
    }

    private fun isDarkMode(): Boolean {
        return when (this.resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
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