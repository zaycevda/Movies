package com.example.movies.domain.usecase

import com.example.movies.data.util.Order
import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend fun execute(order: String = Order.NUM_VOTE.name, keyword: String = EMPTY) =
        repository.getMovies(order = order, keyword = keyword)

    private companion object {
        private const val EMPTY = ""
    }
}