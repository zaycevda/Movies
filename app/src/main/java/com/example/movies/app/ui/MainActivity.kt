package com.example.movies.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movies.R
import com.example.movies.app.di.holder.MoviesComponentHolder
import com.example.movies.app.viewmodel.utils.lazyViewModel
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind, R.id.container_main)

    private val component = MoviesComponentHolder.get()

    private val viewModel by lazyViewModel {
        component.mainViewModel().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        super.onCreate(savedInstanceState)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.moviesFragment ->
                    if (item.isChecked) return@setOnItemSelectedListener true
                    else navController.navigate(R.id.moviesFragment)

                R.id.favoritesFragment ->
                    if (item.isChecked) return@setOnItemSelectedListener true
                    else navController.navigate(R.id.favoritesFragment)
            }
            return@setOnItemSelectedListener true
        }
    }
}