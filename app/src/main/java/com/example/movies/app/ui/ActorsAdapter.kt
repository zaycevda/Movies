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
import com.example.movies.databinding.ItemActorBinding
import com.example.movies.domain.model.Actor

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, DiffUtilCallback())

    var actors: List<Actor>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemActorBinding::bind)

        fun bind(actor: Actor) {
            Glide.with(binding.root.context).load(actor.image).into(binding.ivImage)
            binding.tvName.text = actor.name
            binding.tvCharacter.text = actor.character
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_actor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = actors.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Actor, newItem: Actor) = oldItem == newItem
    }
}