package com.yigithan.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.yigithan.movieapp.R
import com.yigithan.movieapp.adapter.MovieAdapter
import com.yigithan.movieapp.databinding.FragmentHomeBinding
import com.yigithan.movieapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel
    private val movieAdapter = MovieAdapter(arrayListOf())
    private lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.bind(view)

        binding.buttonSearch.setOnClickListener {
            viewModel.showMovieList(binding.etSearch.text.toString())
        }
        moviesRecyclerView.layoutManager = GridLayoutManager(context,2)
        moviesRecyclerView.adapter = movieAdapter
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.moviesRecyclerView.visibility = View.VISIBLE
                movieAdapter.updateMovieList(it)
            }
        })
        viewModel.movieError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.tvError.visibility = View.VISIBLE
                }
                else{
                    binding.tvError.visibility = View.GONE
                }
            }
        })
        viewModel.movieLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.moviesRecyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                else{
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })
    }
}