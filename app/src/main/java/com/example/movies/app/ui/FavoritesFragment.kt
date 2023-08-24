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
import com.example.movies.app.di.holder.FavoritesComponentHolder
import com.example.movies.app.di.utils.featureComponent
import com.example.movies.app.ui.adapter.FavoritesAdapter
import com.example.movies.app.ui.util.showToast
import com.example.movies.app.viewmodel.utils.lazyViewModel
import com.example.movies.databinding.FragmentFavoritesBinding
import com.example.movies.domain.model.Favorite
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val component by featureComponent(holder = FavoritesComponentHolder)

    private val viewModel by lazyViewModel {
        component.favoritesViewModel().create()
    }

    private var adapter: FavoritesAdapter? = null

    private var favorites = mutableListOf<Favorite>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getFavorites()
        deleteAllFavorites()
        initSearch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun initAdapter() {
        adapter = FavoritesAdapter(
            onClick = { id ->
                findNavController().navigate(
                    R.id.action_favoritesFragment_to_movieDetailsFragment,
                    bundleOf(MovieDetailsFragment.MOVIE_ID_KEY to id)
                )
            },
            onLikeAdd = { favorite ->
                viewModel.addFavorite(favorite = favorite)
            },
            onLikeDelete = { id ->
                viewModel.deleteFavorite(id = id)
            }
        )
        binding.rvFavorites.adapter = adapter
    }

    private fun getFavorites() {
        viewModel.getFavorites()
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.favorites.collect { state ->
                    state.on(
                        error = { throwable ->
                            showToast(message = throwable.message.toString())
                        },
                        success = { favorites ->
                            this@FavoritesFragment.favorites = favorites.toMutableList()
                            adapter?.favorites = favorites
                        }
                    )
                }
            }
        }
    }

    private fun deleteAllFavorites() {
        binding.fabDelete.setOnClickListener {
            viewModel.deleteAllFavorites()
            favorites.clear()
        }
    }

    private fun initSearch() {
        binding.svFavorites.clearFocus()
        binding.svFavorites.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false
                override fun onQueryTextChange(newText: String): Boolean {
                    initFilter(newText)
                    return true
                }
            }
        )
    }

    private fun initFilter(text: String) {
        val tempArr = ArrayList<Favorite>()

        for (favorite in favorites)
            if (favorite.title?.lowercase()?.contains(text.lowercase()) == true)
                tempArr.add(favorite)

        adapter?.favorites = tempArr
    }
}