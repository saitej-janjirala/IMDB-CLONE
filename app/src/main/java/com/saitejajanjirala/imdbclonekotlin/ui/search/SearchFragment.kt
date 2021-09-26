package com.saitejajanjirala.imdbclonekotlin.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.saitejajanjirala.imdbclonekotlin.R
import com.saitejajanjirala.imdbclonekotlin.adapters.SearchAdapter
import com.saitejajanjirala.imdbclonekotlin.databinding.FragmentSearchBinding
import com.saitejajanjirala.imdbclonekotlin.models.Result
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(),
    SearchAdapter.onSearchResultClickListener {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var results: ArrayList<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        results = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false))
        bindView()
        return binding.root
    }

    private fun bindView() {
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchResults.layoutManager = lm
        val adapter = SearchAdapter(this, results)
        binding.searchResults.adapter = adapter
        val itemDecoration: ItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        searchViewModel.searchResultMutableLiveData.observe(viewLifecycleOwner, { searchResult ->
            if (searchResult != null) {
                if (searchResult.results!=null && searchResult.results!!.isEmpty()) {
                    binding.searchResults.visibility = View.GONE
                    binding.noResultsLayout.visibility = View.VISIBLE
                } else {
                    binding.searchResults.visibility = View.VISIBLE
                    binding.noResultsLayout.visibility = View.GONE
                }
                results.clear()
                searchResult.results?.let { results.addAll(it) }
                adapter.notifyDataSetChanged()
            }

        })
        binding.backButton.setOnClickListener { v -> closeFragment() }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
                if(p0!=null && !TextUtils.isEmpty(p0)) {
                    searchViewModel.getSearchResults(p0)
                }
                else{
                    Toast.makeText(requireContext(),"Query Cannot be empty",Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        searchViewModel.msg.observe(viewLifecycleOwner,{
            if(it!=null && !TextUtils.isEmpty(it)){
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            }
        })
        searchViewModel.loading.observe(viewLifecycleOwner,{
            binding.progressbar.isVisible=it!=null && it
        })
    }

    override fun onResultClick(result: Result?) {
        if(result!=null) {
            val action: NavDirections =
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(result)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}