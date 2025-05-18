package com.piyush.moviedbtask.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piyush.moviedbtask.databinding.ItemMovieHomeBinding
import com.piyush.moviedbtask.domain.model.Movie

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback()) {

    inner class MovieViewHolder(private val binding: ItemMovieHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Glide.with(binding.ivPoster.context)
                .load(posterUrl)
                .centerInside()
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(old: Movie, new: Movie) = old.id == new.id
        override fun areContentsTheSame(old: Movie, new: Movie) = old == new
    }
}

