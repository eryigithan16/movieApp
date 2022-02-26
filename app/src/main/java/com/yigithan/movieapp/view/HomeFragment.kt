package com.yigithan.movieapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yigithan.movieapp.R
import com.yigithan.movieapp.adapter.MovieAdapter
import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val movieAdapter = MovieAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


        buttonSearch.setOnClickListener {
            val text = etSearch.text
            viewModel.showData(text.toString())
        }


        moviesRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerView.adapter = movieAdapter

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                moviesRecyclerView.visibility = View.VISIBLE
                movieAdapter.updateMovieList(it) //bu movielist falan ne alaka hallet
                Log.e("@@@@@@","$it")
            }
        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    tvError.visibility = View.VISIBLE
                }
                else{
                    tvError.visibility = View.GONE
                }
            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    pbLoading.visibility = View.VISIBLE
                    moviesRecyclerView.visibility = View.GONE
                    tvError.visibility = View.GONE
                }
                else{
                    pbLoading.visibility = View.GONE
                }
            }
        })
    }

}