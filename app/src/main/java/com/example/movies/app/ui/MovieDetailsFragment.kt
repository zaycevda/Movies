package com.example.movies.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.app.di.holder.FavoritesComponentHolder
import com.example.movies.app.di.holder.MoviesComponentHolder
import com.example.movies.app.di.utils.featureComponent
import com.example.movies.app.ui.adapter.ActorsAdapter
import com.example.movies.app.ui.util.showToast
import com.example.movies.app.viewmodel.utils.lazyViewModel
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.domain.model.Favorite
import com.example.movies.domain.model.MovieDetail
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val id by lazy {
        arguments?.getLong(MOVIE_ID_KEY)
    }

    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val favoritesComponent by featureComponent(FavoritesComponentHolder)
    private val favoritesViewModel by lazyViewModel {
        favoritesComponent.favoritesViewModel().create()
    }

    private val moviesComponent by featureComponent(MoviesComponentHolder)
    private val movieDetailsViewModel by lazyViewModel {
        moviesComponent.movieDetailsViewModel().create()
    }

    private var adapter: ActorsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTb()
        initAdapter()
        getMovieDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun initTb() {
        binding.tbMovieDetails.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initAdapter() {
        adapter = ActorsAdapter()
        binding.rvActors.adapter = adapter
    }

    private fun getMovieDetails() {
        id?.let { id -> movieDetailsViewModel.getMovieDetail(id = id) }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.movieDetail.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(message = throwable.message.toString())
                        },
                        success = { movieDetail ->
                            initScreen(movieDetail)
                            openTrailer(movieDetail.video)

                            val favorite =
                                Favorite(
                                    id = movieDetail.id,
                                    preview = movieDetail.poster,
                                    title = movieDetail.title,
                                    rating = movieDetail.rating
                                )
                            workToFavorite(favorite = favorite)
                            initFavorites(favorite = favorite)
                        }
                    )
                }
            }
        }
    }

    private fun initScreen(movieDetail: MovieDetail) {
        Glide.with(requireContext()).load(movieDetail.poster).into(binding.ivPoster)
        binding.tvTitle.text = movieDetail.title
        binding.tvDescription.text = movieDetail.description
        binding.tvRating.text = movieDetail.rating.toString()
        binding.tvPremiere.text = getString(R.string.premiere, movieDetail.premiere.toString())
        adapter?.actors = movieDetail.actors
    }

    private fun openTrailer(url: String?) {
        url?.let {
            binding.btnWatchTrailer.isGone = false
            binding.btnWatchTrailer.setOnClickListener {
                val action = Intent.ACTION_VIEW
                val uri = Uri.parse(url)
                val intent = Intent(action, uri)
                startActivity(intent)
            }
        }
    }

    private fun workToFavorite(favorite: Favorite) {
        binding.ivFavorite.setOnClickListener {
            if (binding.ivFavorite.isSelected) {
                binding.ivFavorite.setIconResource(R.drawable.ic_favorite_disabled)
                favoritesViewModel.deleteFavorite(id = favorite.id)
                binding.ivFavorite.isSelected = false
            } else {
                binding.ivFavorite.setIconResource(R.drawable.ic_favorite_enabled)
                favoritesViewModel.addFavorite(favorite = favorite)
                binding.ivFavorite.isSelected = true
            }
        }
    }

    private fun initFavorites(favorite: Favorite) {
        favoritesViewModel.getFavorites()
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                favoritesViewModel.favorites.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(message = throwable.message.toString())
                        },
                        success = { favorites ->
                            if (favorites.contains(element = favorite)) {
                                binding.ivFavorite.isSelected = true
                                binding.ivFavorite.setIconResource(R.drawable.ic_favorite_enabled)
                            } else {
                                binding.ivFavorite.isSelected = false
                                binding.ivFavorite.setIconResource(R.drawable.ic_favorite_disabled)
                            }
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
    }
}