package com.example.movies.app.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.movies.R
import com.example.movies.app.di.component.FavoritesComponent
import com.example.movies.app.di.holder.FavoritesComponentHolder
import com.example.movies.app.di.holder.MoviesComponentHolder
import com.example.movies.app.di.utils.featureComponent
import com.example.movies.app.ui.adapter.MoviesAdapter
import com.example.movies.app.ui.util.showToast
import com.example.movies.app.viewmodel.utils.lazyViewModel
import com.example.movies.data.util.Order
import com.example.movies.databinding.FragmentMoviesBinding
import kotlinx.coroutines.launch

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding(FragmentMoviesBinding::bind)

    private val favoritesComponent by featureComponent(FavoritesComponentHolder)
    private val favoritesViewModel by lazyViewModel {
        favoritesComponent.favoritesViewModel().create()
    }

    private val moviesComponent by featureComponent(MoviesComponentHolder)
    private val moviesViewModel by lazyViewModel {
        moviesComponent.moviesViewModel().create()
    }

    private var adapter: MoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavorites()
        initAdapter()
        moviesViewModel.getMoviesFromDb()
        getMovies()
        initSearch()
        initSorting()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun initSorting() {
        binding.fabByRating.setOnClickListener {
            moviesViewModel.getMovies(order = Order.RATING.name)
        }
        binding.fabByNumVote.setOnClickListener {
            moviesViewModel.getMovies(order = Order.NUM_VOTE.name)
        }
        binding.favByYear.setOnClickListener {
            moviesViewModel.getMovies(order = Order.YEAR.name)
        }
    }

    private fun getFavorites() {
        favoritesViewModel.getFavorites()
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                favoritesViewModel.favorites.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(message = throwable.message.toString())
                        },
                        success = { favorites ->
                            adapter?.favorites = favorites
                        }
                    )
                }
            }
        }
    }

    private fun initAdapter() {
        adapter = MoviesAdapter(
            onClick = { id ->
                findNavController().navigate(
                    R.id.action_moviesFragment_to_movieDetailsFragment,
                    bundleOf(MovieDetailsFragment.MOVIE_ID_KEY to id)
                )
            },
            onLikeAdd = { favorite ->
                favoritesViewModel.addFavorite(favorite = favorite)
            },
            onLikeDelete = { id ->
                favoritesViewModel.deleteFavorite(id = id)
            }
        )
        binding.rvMovies.adapter = adapter
    }

    private fun getMovies() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                moviesViewModel.movies.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(message = throwable.message.toString())
                        },
                        success = { movies ->
                            adapter?.movies = movies
                        }
                    )
                }
            }
        }
    }

    private fun initSearch() {
        binding.svMovies.clearFocus()
        binding.svMovies.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { keyword ->
                        moviesViewModel.getMovies(keyword = keyword)
                    }
                    return false
                }
            }
        )
    }
}