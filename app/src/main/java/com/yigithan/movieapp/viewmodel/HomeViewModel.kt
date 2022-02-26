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

    val movies = MutableLiveData<Movie>()
    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    fun showData(searchText: String?){
        getDataFromAPI(searchText!!)
    }

    private fun getDataFromAPI(searchText:String?) {
        movieLoading.value = true

        disposable.add(
            movieApiService.getData(searchText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {
                        if(t.movieName==null){
                            movieError.value = true
                            movieLoading.value = false
                        }
                        else{
                            movies.value = t
                            movieError.value = false
                            movieLoading.value = false
                        }

                        Log.e("@@@@@@1","$movies")
                    }

                    override fun onError(e: Throwable) {
                        movieLoading.value = false
                        movieError.value = true
                        e.printStackTrace()
                        Log.e("@@@@@@","gelmedi homeviewmodel")
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}