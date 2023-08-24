package com.example.movies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.app.ui.util.ScreenState
import com.example.movies.app.ui.util.ScreenState.ErrorScreenState
import com.example.movies.app.ui.util.ScreenState.LoadingScreenState
import com.example.movies.app.ui.util.ScreenState.SuccessScreenState
import com.example.movies.domain.model.Favorite
import com.example.movies.domain.usecase.AddFavoriteUseCase
import com.example.movies.domain.usecase.DeleteAllFavoritesUseCase
import com.example.movies.domain.usecase.DeleteFavoriteUseCase
import com.example.movies.domain.usecase.GetFavoritesUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel @AssistedInject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteAllFavoritesUseCase: DeleteAllFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _favorites =
        MutableStateFlow<ScreenState<List<Favorite>>>(value = LoadingScreenState())
    val favorites = _favorites.asStateFlow()

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            addFavoriteUseCase.execute(favorite = favorite)
        }
    }

    fun deleteAllFavorites() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _favorites.value = ErrorScreenState(throwable = throwable)
        }

        viewModelScope.launch(context = exceptionHandler) {
            _favorites.value = LoadingScreenState()
            deleteAllFavoritesUseCase.execute()
            _favorites.value = SuccessScreenState(data = listOf())
        }
    }

    fun deleteFavorite(id: Long) {
        viewModelScope.launch {
            deleteFavoriteUseCase.execute(id = id)
        }
    }

    fun getFavorites() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _favorites.value = ErrorScreenState(throwable = throwable)
        }

        viewModelScope.launch(context = exceptionHandler) {
            _favorites.value = LoadingScreenState()
            val favorites = getFavoritesUseCase.execute()
            _favorites.value = SuccessScreenState(data = favorites)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): FavoritesViewModel
    }
}