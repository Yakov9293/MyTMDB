package com.example.mytmdb.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.mytmdb.fragments.MovieFragmentArgs
import com.example.mytmdb.R

class MovieFragment : Fragment() {
    val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //temp.text = arguments?.getInt("movieId").toString()
        Log.d("MovieFragment", "ID фильма: ${args.movieId}")
    }
}
