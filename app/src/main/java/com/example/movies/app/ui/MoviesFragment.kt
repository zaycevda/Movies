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
import com.example.movies.app.di.holder.MoviesComponentHolder
import com.example.movies.app.di.utils.featureComponent
import com.example.movies.app.ui.utils.showToast
import com.example.movies.app.viewmodel.utils.lazyViewModel
import com.example.movies.databinding.FragmentMoviesBinding
import kotlinx.coroutines.launch

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding(FragmentMoviesBinding::bind)

    private val component by featureComponent(MoviesComponentHolder)

    private val viewModel by lazyViewModel {
        component.moviesViewModel().create()
    }

    private var adapter: MoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getMovies()
        initSearch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun initAdapter() {
        adapter = MoviesAdapter { id ->
            findNavController().navigate(
                R.id.action_moviesFragment_to_movieDetailsFragment,
                bundleOf(MovieDetailsFragment.MOVIE_ID_KEY to id)
            )
        }
        binding.rvMovies.adapter = adapter
    }

    private fun getMovies(keyword: String = EMPTY) {
        viewModel.getMovies(keyword = keyword)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(throwable.message.toString())
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
                        getMovies(keyword = keyword)
                    }
                    return false
                }
            }
        )
    }

    private companion object {
        private const val EMPTY = ""
    }
}