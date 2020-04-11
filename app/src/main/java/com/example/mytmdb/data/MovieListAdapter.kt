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
import com.example.mytmdb.fragments.MoviesFragmentDirections
import com.timqi.sectorprogressview.ColorfulRingProgressView

class MovieListAdapter() :
    ListAdapter<SimplifiedMovie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.movies_list_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title).text = getItem(position).title
        holder.itemView.findViewById<TextView>(R.id.original_title).text = getItem(position).original_title
        holder.itemView.findViewById<TextView>(R.id.ratingDigit).text =
            "${(getItem(position).vote_average * 10).toInt()}%"
        holder.itemView.findViewById<ColorfulRingProgressView>(R.id.rating).percent =
            (getItem(position).vote_average * 10).toFloat()
        holder.itemView.setOnClickListener {
            val directions =
                MoviesFragmentDirections.actionMoviesFragmentToMovieFragment(
                    getItem(position).id
                )
            it.findNavController().navigate(directions)
        }
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500${getItem(position).poster_path}")
            .into(holder.itemView.findViewById(R.id.poster))
    }
}

/*RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {
    class MoviesListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movies_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.title).text = data[position].title
        holder.view.findViewById<TextView>(R.id.original_title).text = data[position].original_title
        holder.view.findViewById<TextView>(R.id.ratingDigit).text =
            "${(data[position].vote_average * 10).toInt()}%"
        holder.view.findViewById<ColorfulRingProgressView>(R.id.rating).percent =
            (data[position].vote_average * 10).toFloat()
        holder.view.setOnClickListener {
            val directions =
                MoviesFragmentDirections.actionMoviesFragmentToMovieFragment(
                    data[position].id
                )
            it.findNavController().navigate(directions)
        }
        Glide.with(holder.view).load("https://image.tmdb.org/t/p/w500${data[position].poster_path}")
            .into(holder.view.findViewById(R.id.poster))

    }
}*/

private class MovieDiffCallback : DiffUtil.ItemCallback<SimplifiedMovie>() {

    override fun areItemsTheSame(oldItem: SimplifiedMovie, newItem: SimplifiedMovie): Boolean {
        val res = oldItem.id == newItem.id
        Log.d("MovieListAdapter: items",  res.toString())
        return res
    }

    override fun areContentsTheSame(oldItem: SimplifiedMovie, newItem: SimplifiedMovie): Boolean {
        val res = (oldItem.title == newItem.title && oldItem.original_title == newItem.original_title && oldItem.poster_path == newItem.poster_path && oldItem.vote_average == newItem.vote_average)
        Log.d("MovieListAdapter: cont",  res.toString())
        return res
    }
}