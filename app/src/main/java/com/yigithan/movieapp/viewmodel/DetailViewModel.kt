package com.yigithan.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.model.Search
import com.yigithan.movieapp.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    val movieLiveData = MutableLiveData<Movie>()
    val detailMovieLoading = MutableLiveData<Boolean>()
    val detailMovieError = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val movieApiService = MovieAPIService()

    fun showMovieDetail(id: String){
        getMovieDetailFromAPI(id)
    }

    private fun getMovieDetailFromAPI(id: String){
        detailMovieLoading.value = true
        disposable.add(
            movieApiService.getData(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {
                        detailMovieLoading.value = false
                        movieLiveData.value = t
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        detailMovieLoading.value = false
                        detailMovieError.value = true
                    }
                })
        )
    }
}