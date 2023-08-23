package com.example.movies.app.di.holder

import com.example.movies.app.App
import com.example.movies.app.di.component.DaggerMoviesComponent
import com.example.movies.app.di.component.MoviesComponent
import com.example.movies.app.di.module.MoviesModule

object MoviesComponentHolder : FeatureComponentHolder<MoviesComponent>() {
    override fun build(): MoviesComponent {
        return DaggerMoviesComponent.builder()
            .appComponent(App.component)
            .moviesModule(MoviesModule())
            .build()
    }
}