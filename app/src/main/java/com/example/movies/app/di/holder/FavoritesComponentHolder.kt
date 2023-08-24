package com.example.movies.app.di.holder

import com.example.movies.app.App
import com.example.movies.app.di.component.DaggerFavoritesComponent
import com.example.movies.app.di.component.FavoritesComponent
import com.example.movies.app.di.module.FavoritesModule

object FavoritesComponentHolder : FeatureComponentHolder<FavoritesComponent>() {
    override fun build(): FavoritesComponent =
        DaggerFavoritesComponent.builder()
            .appComponent(App.component)
            .favoritesModule(FavoritesModule())
            .build()
}