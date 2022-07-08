package com.example.marvelapp.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.presentation.common.extensions.viewBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), ActivityCallback {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarApp)
        setNavigation()
        firebaseAnalytics = Firebase.analytics
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
        Firebase.crashlytics.log("MainActivity - onCreate")
    }

    private fun setNavigation() {
        Firebase.crashlytics.log("MainActivity - setNavigation()")
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavMain.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment,
                R.id.favoritesFragment,
                R.id.aboutFragment,
                R.id.sortFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
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
}