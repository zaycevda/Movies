package com.example.movies.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemMovieBinding
import com.example.movies.domain.model.Movie

typealias OnClick = (id: Long) -> Unit

class MoviesAdapter(private val onClick: OnClick) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, DiffUtilCallback())

    var movies: List<Movie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemMovieBinding::bind)

        fun bind(movie: Movie) {
            Glide.with(binding.root.context).load(movie.preview).into(binding.ivPreview)
            binding.tvTitle.text = movie.title
            binding.tvRating.text = movie.rating.toString()

            itemView.setOnClickListener {
                onClick(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}