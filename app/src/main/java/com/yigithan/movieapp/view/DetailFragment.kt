package com.yigithan.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yigithan.movieapp.R
import com.yigithan.movieapp.model.Movie
import com.yigithan.movieapp.util.downloadFromUrl
import com.yigithan.movieapp.util.placeHolderProgressBar
import com.yigithan.movieapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    lateinit var selectedMovie: Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            selectedMovie = DetailFragmentArgs.fromBundle(it).argMovie
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getData(selectedMovie)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                detailTitle.text = movie.movieName
                detailGenre.text = movie.movieGenre
                detailPlot.text = movie.moviePlot
                context?.let {
                    detailImage.downloadFromUrl(movie.moviePhoto, placeHolderProgressBar(it))
                }

            }
        })
    }
}