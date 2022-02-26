package com.yigithan.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yigithan.movieapp.model.Movie

class DetailViewModel : ViewModel() {

    val movieLiveData = MutableLiveData<Movie>()

    fun getData(movie: Movie){
        movieLiveData.value = movie
    }
}