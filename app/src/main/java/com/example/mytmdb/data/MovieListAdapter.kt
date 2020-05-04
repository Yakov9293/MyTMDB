package com.example.mytmdb.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytmdb.R
import com.example.mytmdb.databinding.MoviesListItemBinding
import com.example.mytmdb.databinding.NetworkStateItemBinding
import com.example.mytmdb.fragments.MoviesFragmentDirections
import com.example.mytmdb.model.NetworkState

class MovieListAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<SimplifiedMovie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private var networkState: NetworkState? = null
    private var initialNetworkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.network_state_item -> NetworkViewHolder(
                NetworkStateItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
            )
            R.layout.movies_list_item -> MovieViewHolder(
                MoviesListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.network_state_item -> (holder as NetworkViewHolder).bind(
                networkState,
                initialNetworkState,
                retryCallback
            )
            R.layout.movies_list_item -> {
                val movie = getItem(position)
                (holder as MovieViewHolder).bind(movie)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.movies_list_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    class MovieViewHolder(private val binding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.movie?.let { movie ->
                    navigateToDetailMovie(movie, it)
                }
            }
        }

        fun bind(item: SimplifiedMovie?) {
            binding.apply {
                movie = item
                executePendingBindings()
            }
        }

        private fun navigateToDetailMovie(movie: SimplifiedMovie, view: View) {
            val direction =
                MoviesFragmentDirections.actionMoviesFragmentToMovieFragment(
                    movie.id
                )
            view.findNavController().navigate(direction)
        }
    }

    class NetworkViewHolder(
        private val binding: NetworkStateItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(state: NetworkState?, initState: NetworkState?, retryCallback: () -> Unit) {
            binding.apply {
                networkState = state
                initialNetworkState = initState
                setRetryCallback { retryCallback() }
                executePendingBindings()
            }
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?, initialNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        this.initialNetworkState = initialNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
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