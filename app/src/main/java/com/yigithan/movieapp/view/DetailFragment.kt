package com.yigithan.movieapp.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.yigithan.movieapp.R
import com.yigithan.movieapp.util.downloadFromUrl
import com.yigithan.movieapp.util.placeHolderProgressBar
import com.yigithan.movieapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    lateinit var selectedMovieId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            selectedMovieId = DetailFragmentArgs.fromBundle(it).argMovie!!
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.showMovieDetail(selectedMovieId)
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                tvRuntime.text = movie.movieRuntime
                tvDirectorName.text = movie.movieDirector
                tvImdbRate.text = "${movie.movieImdb}/10"
                tvDetailMovieTitle.text = movie.movieName
                tvDetailGenre.text = movie.movieGenre
                tvDetailSynopsis.text = movie.moviePlot
                tvDetailSynopsis.movementMethod = ScrollingMovementMethod()
                tvDetailMovieTitle.isSelected = true
                tvDirectorName.isSelected = true
                context?.let {
                    detailImage.downloadFromUrl(movie.moviePhoto, placeHolderProgressBar(it))
                }
                layoutDetail.visibility = View.VISIBLE
            }
        })
        viewModel.detailMovieError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    tvDetailError.visibility = View.VISIBLE
                }
                else{
                    tvDetailError.visibility = View.GONE
                }
            }
        })
        viewModel.detailMovieLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                pbDetail.visibility = View.VISIBLE
                layoutDetail.visibility = View.GONE
                tvDetailError.visibility = View.GONE
            }
            else{
                pbDetail.visibility = View.GONE
            }
        })
    }
}