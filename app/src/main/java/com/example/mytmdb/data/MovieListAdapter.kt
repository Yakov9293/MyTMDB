package com.example.mytmdb.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytmdb.R
import com.example.mytmdb.databinding.MoviesListItemBinding
import com.example.mytmdb.fragments.MoviesFragmentDirections
import com.timqi.sectorprogressview.ColorfulRingProgressView

class MovieListAdapter() :
    ListAdapter<SimplifiedMovie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(private val binding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimplifiedMovie) {
            binding.apply {
                movie = item
                setClickListener {
                    navigateToPlant(item, it)
                }
                executePendingBindings()
            }
        }

        private fun navigateToPlant(movie: SimplifiedMovie, view: View) {
            val direction =
                MoviesFragmentDirections.actionMoviesFragmentToMovieFragment(
                    movie.id
                )
            view.findNavController().navigate(direction)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            MoviesListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        (holder as MovieViewHolder).bind(movie)
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<SimplifiedMovie>() {

    override fun areItemsTheSame(oldItem: SimplifiedMovie, newItem: SimplifiedMovie): Boolean {
        val res = oldItem.id == newItem.id
        Log.d("MovieListAdapter: items", res.toString())
        return res
    }

    override fun areContentsTheSame(oldItem: SimplifiedMovie, newItem: SimplifiedMovie): Boolean {
        val res =
            (oldItem.title == newItem.title && oldItem.original_title == newItem.original_title && oldItem.poster_path == newItem.poster_path && oldItem.vote_average == newItem.vote_average)
        Log.d("MovieListAdapter: cont", res.toString())
        return res
    }
}