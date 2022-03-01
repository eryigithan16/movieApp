package com.yigithan.movieapp.adapter

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

class MovieAdapter(var movieList: ArrayList<Movie>):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(var view: View) : RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.tvMovieTitle.text = movieList[position].movieName
        holder.view.tvMovieYear.text = movieList[position].movieYear
        holder.view.ivMovieImage.downloadFromUrl(movieList[position].moviePhoto, placeHolderProgressBar(holder.view.context))

        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieList[position].movieName)
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.tvMovieTitle.isSelected = true
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateMovieList(newMovieList : List<Movie>){
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }
}