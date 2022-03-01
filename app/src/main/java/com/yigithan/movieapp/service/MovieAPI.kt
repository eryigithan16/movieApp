package com.yigithan.movieapp.service

import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.model.Search
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "37eff96c"
const val FULL = "full"
interface MovieAPI {
    @GET("?&apikey=37eff96c")
    fun getMovie(
        @Query("t") searchText:String,
        @Query("plot") plotText : String = FULL): Single<Movie>


    @GET("/")
    fun getSearchMovies(
        @Query("s") searchText: String,
        @Query("apikey") ombd_api_key: String = API_KEY): Single<Search>

}