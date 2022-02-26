package com.yigithan.movieapp.service

import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.model.Search
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "37eff96c"

interface MovieAPI {
    @GET("?&apikey=37eff96c")
    fun getMovies(
        @Query("t") searchText:String
    ): Single<Movie>
}