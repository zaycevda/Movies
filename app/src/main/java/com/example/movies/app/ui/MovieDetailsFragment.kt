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
import com.example.movies.app.di.holder.MoviesComponentHolder
import com.example.movies.app.di.utils.featureComponent
import com.example.movies.app.ui.utils.showToast
import com.example.movies.app.viewmodel.utils.lazyViewModel
import com.example.movies.databinding.FragmentMovieDetailsBinding
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val id by lazy {
        arguments?.getLong(MOVIE_ID_KEY)
    }

    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val component by featureComponent(MoviesComponentHolder)

    private val viewModel by lazyViewModel {
        component.movieDetailsViewModel().create()
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
        id?.let { id -> viewModel.getMovieDetail(id = id) }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetail.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(message = throwable.message.toString())
                        },
                        success = { movieDetail ->
                            Glide.with(requireContext()).load(movieDetail.poster).into(binding.ivPoster)
                            binding.tvTitle.text = movieDetail.title
                            binding.tvDescription.text = movieDetail.description
                            binding.tvRating.text = movieDetail.rating.toString()
                            binding.tvPremiere.text = getString(R.string.premiere, movieDetail.premiere.toString())
                            adapter?.actors = movieDetail.actors

                            movieDetail.video?.let { url ->
                                binding.btnWatchTrailer.isGone = false
                                binding.btnWatchTrailer.setOnClickListener {
                                    val action = Intent.ACTION_VIEW
                                    val uri = Uri.parse(url)
                                    val intent = Intent(action, uri)
                                    startActivity(intent)
                                }
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