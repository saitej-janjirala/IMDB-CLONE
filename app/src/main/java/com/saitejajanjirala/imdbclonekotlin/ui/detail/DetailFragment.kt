package com.saitejajanjirala.imdbclonekotlin.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.saitejajanjirala.imdbclonekotlin.R
import com.saitejajanjirala.imdbclonekotlin.adapters.GenreAdapter
import com.saitejajanjirala.imdbclonekotlin.databinding.FragmentDetailBinding
import com.saitejajanjirala.imdbclonekotlin.models.Result
import com.saitejajanjirala.imdbclonekotlin.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.ArrayList

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding:FragmentDetailBinding
    private lateinit var result:Result
    var genres: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        genres = ArrayList()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.bind(view)
        result = DetailFragmentArgs.fromBundle(requireArguments()).result
        bindView()
        return binding.root
    }

    private fun bindView() {
        if (result != null) {
            result.backdrop_path?.let { Helper.loadImage(requireContext(), it, binding.coverImage) }
            result.poster_path?.let { Helper.loadImage(requireContext(), it, binding.posterImage) }
            result.genre_ids?.let { Helper.getGenresFromIds(it)?.let { genres!!.addAll(it) } }
            binding.rating.text = result.vote_average.toString() + ""
            binding.title.text = result.title
            binding.topTitle.text = result.title
            binding.overView.text = result.overview
            binding.voters.text = result.vote_count.toString() + ""
        }
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.generesRecyclerview.layoutManager = layoutManager
        val adapter = genres?.let { GenreAdapter(it) }
        binding.generesRecyclerview.adapter = adapter
        binding.backButton.setOnClickListener { closeFragment() }
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}