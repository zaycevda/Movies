package com.example.movies.app.di.module

import com.example.movies.app.di.scope.MovieScope
import com.example.movies.data.db.db.RoomDb
import com.example.movies.data.db.repository.MoviesRepositoryDbImpl
import com.example.movies.data.net.repository.MoviesRepositoryImpl
import com.example.movies.data.net.service.MoviesApi
import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.repository.MoviesRepositoryDb
import com.example.movies.domain.usecase.AddMoviesUseCase
import com.example.movies.domain.usecase.GetMovieDetailUseCase
import com.example.movies.domain.usecase.GetMoviesFromDbUseCase
import com.example.movies.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MoviesModule {
    @MovieScope
    @Provides
    fun provideMoviesRepository(api: MoviesApi): MoviesRepository =
        MoviesRepositoryImpl(api = api)

    @MovieScope
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)

    @MovieScope
    @Provides
    fun provideGetMoviesUseCase(repository: MoviesRepository) =
        GetMoviesUseCase(repository = repository)

    @MovieScope
    @Provides
    fun provideGetMovieDetailsUseCase(repository: MoviesRepository) =
        GetMovieDetailUseCase(repository = repository)

    @MovieScope
    @Provides
    fun provideMoviesRepositoryDb(db: RoomDb): MoviesRepositoryDb =
        MoviesRepositoryDbImpl(db = db)

    @MovieScope
    @Provides
    fun provideAddMovies(repository: MoviesRepositoryDb) =
        AddMoviesUseCase(repository = repository)

    @MovieScope
    @Provides
    fun provideGetMoviesFromDbUseCase(repository: MoviesRepositoryDb) =
        GetMoviesFromDbUseCase(repository = repository)
}