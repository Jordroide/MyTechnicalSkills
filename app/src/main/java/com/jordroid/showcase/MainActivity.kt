package com.jordroid.showcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jordroid.showcase.databinding.QuoteActivityBinding

class MainActivity : AppCompatActivity() {

    // Binding of view
    private lateinit var binding: QuoteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = QuoteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup bottom navigation view to use navigation api
        supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)?.let {
            setupActionBarWithNavController(
                navController = it.findNavController(),
                configuration = AppBarConfiguration(
                    topLevelDestinationIds = setOf(
                        R.id.navigation_fragment_quote,
                        R.id.navigation_fragment_list,
                        R.id.navigation_fragment_gallery
                    )
                )
            )
            binding.bottomNavigation.setupWithNavController(it.findNavController())
        }
    }
}
