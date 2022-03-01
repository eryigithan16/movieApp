package com.yigithan.movieapp.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.yigithan.movieapp.R
import com.yigithan.movieapp.databinding.FragmentDetailBinding
import com.yigithan.movieapp.util.downloadFromUrl
import com.yigithan.movieapp.util.placeHolderProgressBar
import com.yigithan.movieapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var viewModel: DetailViewModel
    lateinit var selectedMovieId: String
    lateinit var binding : FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        arguments?.let {
            selectedMovieId = DetailFragmentArgs.fromBundle(it).argMovie!!
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.showMovieDetail(selectedMovieId)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let {
                binding.tvRuntime.text = movie.movieRuntime
                binding.tvDirectorName.text = movie.movieDirector
                binding.tvImdbRate.text = "${movie.movieImdb}/10"
                binding.tvDetailMovieTitle.text = movie.movieName
                binding.tvDetailGenre.text = movie.movieGenre
                binding.tvDetailSynopsis.text = movie.moviePlot
                binding.tvDetailSynopsis.movementMethod = ScrollingMovementMethod()
                binding.tvDetailMovieTitle.isSelected = true
                binding.tvDirectorName.isSelected = true
                context?.let {
                    binding.ivDetailImage.downloadFromUrl(movie.moviePhoto, placeHolderProgressBar(it))
                }
                binding.layoutDetail.visibility = View.VISIBLE
            }
        })
        viewModel.detailMovieError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.tvDetailError.visibility = View.VISIBLE
                }
                else{
                    binding.tvDetailError.visibility = View.GONE
                }
            }
        })
        viewModel.detailMovieLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.pbDetail.visibility = View.VISIBLE
                binding.layoutDetail.visibility = View.GONE
                binding.tvDetailError.visibility = View.GONE
            }
            else{
                binding.pbDetail.visibility = View.GONE
            }
        })
    }
}