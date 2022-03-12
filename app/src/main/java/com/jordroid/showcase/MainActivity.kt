package com.jordroid.showcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jordroid.showcase.databinding.QuoteActivityBinding

class MainActivity : AppCompatActivity() {

    // Binding of view
    private lateinit var binding: QuoteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QuoteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup bottom navigation view to use navigation api
        supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)?.let {
            binding.bottomNavigation.setupWithNavController(it.findNavController())
        }
    }
}
