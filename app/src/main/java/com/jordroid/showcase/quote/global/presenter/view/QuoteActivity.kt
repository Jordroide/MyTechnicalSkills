package com.jordroid.showcase.quote.global.presenter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jordroid.showcase.R
import com.jordroid.showcase.databinding.QuoteActivityBinding

class QuoteActivity : AppCompatActivity() {

    private lateinit var binding: QuoteActivityBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QuoteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.fragment_main_activity)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_fragment_all, R.id.navigation_fragment_anime)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}