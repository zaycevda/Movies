package com.example.movies.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.movies.R
import com.example.movies.app.di.holder.MoviesComponentHolder
import com.example.movies.app.viewmodel.utils.lazyViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
    }
}