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

class HomeViewModel : ViewModel() {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<List<Movie>>()
    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    fun showMovieList(searchText: String?){
        getMovieListFromAPI(searchText!!)
    }

    private fun getMovieListFromAPI(searchText:String?) {
        movieLoading.value = true
        disposable.add(
            movieApiService.getDataList(searchText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Search>(){
                    override fun onSuccess(t: Search) {
                        if(t.resultSearch.isNullOrEmpty()){
                            movieError.value = true
                            movieLoading.value = false
                        }
                        else{
                            movies.value = t.resultSearch
                            movieError.value = false
                            movieLoading.value = false
                        }
                    }
                    override fun onError(e: Throwable) {
                        movieLoading.value = false
                        movieError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}