package com.saitejajanjirala.imdbclonekotlin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.saitejajanjirala.imdbclonekotlin.adapters.HomePageAdapters
import com.saitejajanjirala.imdbclonekotlin.databinding.FragmentHomeBinding
import com.saitejajanjirala.imdbclonekotlin.listeners.BookMarkListener
import com.saitejajanjirala.imdbclonekotlin.listeners.BookMarkStatusesListener
import com.saitejajanjirala.imdbclonekotlin.listeners.listenerobject.BookMarkListenerObject
import com.saitejajanjirala.imdbclonekotlin.models.Result
import com.saitejajanjirala.imdbclonekotlin.utils.Keys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomePageAdapters.onHomePageItemClickListener,
    BookMarkStatusesListener {

    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var nowPlayingAdapter: HomePageAdapters
    private lateinit var popularAdapter: HomePageAdapters
    private lateinit var nowPlayingList: MutableList<Result>
    private lateinit var popularList: MutableList<Result>
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nowPlayingList = mutableListOf()
        popularList = mutableListOf()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRootView()
        setUpObservers()
        return binding!!.getRoot()
    }

    open fun initRootView() {
        val l1 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val l2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.nowPlayingRecyclerview.setLayoutManager(l1)
        binding.popularRecyclerview.setLayoutManager(l2)
        nowPlayingAdapter = HomePageAdapters(nowPlayingList!!, requireContext(), this, 0)
        popularAdapter = HomePageAdapters(popularList!!, requireContext(), this, 0)
        binding.nowPlayingRecyclerview.setAdapter(nowPlayingAdapter)
        binding.popularRecyclerview.setAdapter(popularAdapter)
        binding.nowPlayingRecyclerview.setNestedScrollingEnabled(false)
        binding.popularRecyclerview.setNestedScrollingEnabled(false)
    }

    open fun setUpObservers() {
        binding.searchView.setOnClickListener { v ->
            val action: NavDirections = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(binding.getRoot()).navigate(action)
        }
        mViewModel.nowPlayingMutableLiveData.observe(getViewLifecycleOwner()) { results ->
            nowPlayingList!!.clear()
            nowPlayingList!!.addAll(results)
            nowPlayingAdapter.notifyDataSetChanged()
            binding.homeScroll.isVisible = nowPlayingList.size != 0
            binding.noResultsLayout.isVisible = nowPlayingList.size == 0
        }
        mViewModel.popularMutableLiveData.observe(getViewLifecycleOwner()) { results ->
            popularList!!.clear()
            popularList!!.addAll(results)
            popularAdapter.notifyDataSetChanged()
            binding.homeScroll.isVisible = popularList.size != 0
            binding.noResultsLayout.isVisible = popularList.size == 0
        }
        binding.bookMarks.setOnClickListener { v ->
            val bookMarkListenerObject = BookMarkListenerObject(this)
            val action: NavDirections =
                HomeFragmentDirections.actionHomeFragmentToBookMarkFragment(bookMarkListenerObject)
            Navigation.findNavController(binding.getRoot()).navigate(action)
        }
        binding.retryButton.setOnClickListener { v -> mViewModel.getData() }
        mViewModel.loading.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it != null && it
        })
        mViewModel.msg.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(result: Result?) {
        result?.let {
            val action: NavDirections =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            Navigation.findNavController(binding.getRoot()).navigate(action)
        }
    }

    override fun onBookMarkClicked(result: Result?, position: Int, listener: BookMarkListener?) {
        if (result != null && listener != null) {
            mViewModel.setBookMarkStatus(result, position, listener)
        }
    }


    override fun onItemChanged(result: Result?) {
        val type: String = result?.type!!
        Log.i("itemchange", result.toString())
        var i = -1
        if (type == Keys.NOW_PLAYING) {
            for (res in nowPlayingList) {
                if (res != null) {
                    if (result != null) {
                        if (res.id === result.id) {
                            i = nowPlayingList!!.indexOf(res)
                            break
                        }
                    }
                }
            }
            if (i != -1) {
                val res: Result = nowPlayingList[i]!!
                res.bookmarked = !res.bookmarked
                nowPlayingList[i] = res
                nowPlayingAdapter.notifyItemChanged(i)
            }
        } else {
            for (res in popularList) {
                if (res != null) {
                    if (res.id === result.id) {
                        i = popularList!!.indexOf(res)
                        break
                    }
                }
            }
            if (i != -1) {
                val res: Result = popularList[i]!!
                res.bookmarked = !res.bookmarked
                popularList[i] = res
                popularAdapter.notifyItemChanged(i)
            }
        }
    }


    override fun onAllCleared(size: Int) {
        Log.i("cleared", size.toString() + "")
        if (size > 0) {
            for (i in popularList) {
                if (i != null) {
                    i.bookmarked = false
                }
            }
            for (i in nowPlayingList) {
                if (i != null) {
                    i.bookmarked = false
                }
            }
            popularAdapter.notifyDataSetChanged()
            nowPlayingAdapter.notifyDataSetChanged()
        }
    }
}