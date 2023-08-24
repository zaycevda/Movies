package com.example.movies.app.di.module

import com.example.movies.app.di.scope.FavoriteScope
import com.example.movies.data.db.db.RoomDb
import com.example.movies.data.db.repository.FavoritesRepositoryImpl
import com.example.movies.domain.repository.FavoritesRepository
import com.example.movies.domain.usecase.AddFavoriteUseCase
import com.example.movies.domain.usecase.DeleteAllFavoritesUseCase
import com.example.movies.domain.usecase.DeleteFavoriteUseCase
import com.example.movies.domain.usecase.GetFavoritesUseCase
import dagger.Module
import dagger.Provides

@Module
class FavoritesModule {
    @FavoriteScope
    @Provides
    fun provideFavoritesRepository(db: RoomDb): FavoritesRepository =
        FavoritesRepositoryImpl(db = db)

    @FavoriteScope
    @Provides
    fun provideAddFavoriteUseCase(repository: FavoritesRepository) =
        AddFavoriteUseCase(repository = repository)

    @FavoriteScope
    @Provides
    fun provideDeleteAllFavoritesUseCase(repository: FavoritesRepository) =
        DeleteAllFavoritesUseCase(repository = repository)

    @FavoriteScope
    @Provides
    fun provideDeleteFavoriteUseCase(repository: FavoritesRepository) =
        DeleteFavoriteUseCase(repository = repository)

    @FavoriteScope
    @Provides
    fun provideGetFavoritesUseCase(repository: FavoritesRepository) =
        GetFavoritesUseCase(repository = repository)
}