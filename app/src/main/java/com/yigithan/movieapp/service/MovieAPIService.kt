package com.yigithan.movieapp.service

import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.model.Search
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    private val BASE_URL = "http://www.omdbapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getData(searchText:String?) : Single<Movie>{
        return api.getMovies(searchText!!)
    }
}