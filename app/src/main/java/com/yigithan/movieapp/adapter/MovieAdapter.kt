package com.yigithan.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yigithan.movieapp.R
import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.util.downloadFromUrl
import com.yigithan.movieapp.util.placeHolderProgressBar
import com.yigithan.movieapp.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var movie: Movie?):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(var view: View) : RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (movie != null){
            holder.view.movieTitle.text = movie!!.movieName
            holder.view.movieGenre.text = movie!!.movieGenre
            holder.view.moviePlot.text = movie!!.moviePlot
            holder.view.movieImage.downloadFromUrl(movie!!.moviePhoto, placeHolderProgressBar(holder.view.context))

            holder.view.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie!!)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun updateMovieList(newMovie:Movie){
        movie = newMovie
        notifyDataSetChanged()
    }
}