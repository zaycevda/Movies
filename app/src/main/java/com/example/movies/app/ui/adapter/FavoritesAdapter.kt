package com.example.movies.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.app.ui.adapter.FavoritesAdapter.ViewHolder
import com.example.movies.databinding.ItemMovieBinding
import com.example.movies.domain.model.Favorite

class FavoritesAdapter(
    private val onClick: OnClick,
    private val onLikeAdd: OnLikeAdd,
    private val onLikeDelete: OnLikeDelete
) : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(this, DiffUtilCallback())

    var favorites: List<Favorite>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemMovieBinding::bind)

        fun bind(favorite: Favorite) {
            Glide.with(binding.root.context).load(favorite.preview).into(binding.ivPreview)
            binding.tvTitle.text = favorite.title
            binding.tvRating.text = favorite.rating.toString()

            itemView.setOnClickListener {
                onClick(favorite.id)
            }

            initFavorites(favorite = favorite)
            workToFavorite(favorite = favorite)
        }

        private fun workToFavorite(favorite: Favorite) {
            binding.ivFavorite.setOnClickListener {
                if (binding.ivFavorite.isSelected) {
                    binding.ivFavorite.setIconResource(R.drawable.ic_favorite_disabled)
                    onLikeDelete(favorite.id)
                    binding.ivFavorite.isSelected = false
                } else {
                    binding.ivFavorite.setIconResource(R.drawable.ic_favorite_enabled)
                    onLikeAdd(favorite)
                    binding.ivFavorite.isSelected = true
                }
            }
        }

        private fun initFavorites(favorite: Favorite) {
            if (favorites.contains(element = favorite)) {
                binding.ivFavorite.setIconResource(R.drawable.ic_favorite_enabled)
                binding.ivFavorite.isSelected = true
            } else {
                binding.ivFavorite.setIconResource(R.drawable.ic_favorite_disabled)
                binding.ivFavorite.isSelected = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view = view)
    }

    override fun getItemCount() = favorites.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorite = favorites[position])
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite) = oldItem == newItem
    }
}