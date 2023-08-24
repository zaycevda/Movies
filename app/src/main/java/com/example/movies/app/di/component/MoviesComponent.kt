package com.example.movies.app.di.component

import com.example.movies.app.di.module.MoviesModule
import com.example.movies.app.di.scope.MovieScope
import com.example.movies.app.viewmodel.MainViewModel
import com.example.movies.app.viewmodel.MovieDetailsViewModel
import com.example.movies.app.viewmodel.MoviesViewModel
import dagger.Component

@MovieScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MoviesModule::class]
)
interface MoviesComponent : DiComponent {
    fun mainViewModel(): MainViewModel.Factory
    fun moviesViewModel(): MoviesViewModel.Factory
    fun movieDetailsViewModel(): MovieDetailsViewModel.Factory
}