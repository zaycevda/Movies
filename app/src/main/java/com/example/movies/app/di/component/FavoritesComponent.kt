package com.example.movies.app.di.component

import com.example.movies.app.di.module.FavoritesModule
import com.example.movies.app.di.scope.FavoriteScope
import com.example.movies.app.viewmodel.FavoritesViewModel
import dagger.Component

@FavoriteScope
@Component(
    dependencies = [AppComponent::class],
    modules = [FavoritesModule::class]
)
interface FavoritesComponent : DiComponent {
    fun favoritesViewModel(): FavoritesViewModel.Factory
}