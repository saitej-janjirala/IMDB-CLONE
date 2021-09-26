package com.saitejajanjirala.imdbclonekotlin.ui.bookmark

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saitejajanjirala.imdbclonekotlin.R
import com.saitejajanjirala.imdbclonekotlin.adapters.HomePageAdapters
import com.saitejajanjirala.imdbclonekotlin.databinding.FragmentBookMarkBinding
import com.saitejajanjirala.imdbclonekotlin.listeners.BookMarkListener
import com.saitejajanjirala.imdbclonekotlin.listeners.listenerobject.BookMarkListenerObject
import com.saitejajanjirala.imdbclonekotlin.models.Result
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BookMarkFragment : Fragment() ,HomePageAdapters.onHomePageItemClickListener{

    private lateinit var binding: FragmentBookMarkBinding
    private val bookMarkViewModel: BookMarkViewModel by viewModels()
    private lateinit var bookMarkResults: MutableList<Result>
    private lateinit var bookMarkAdapter: HomePageAdapters
    private lateinit var bmObject: BookMarkListenerObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookMarkResults = ArrayList<Result>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_mark, container, false)
        binding = FragmentBookMarkBinding.bind(view)
        bmObject = BookMarkFragmentArgs.fromBundle(requireArguments()).bmobject
        setUp()
        return binding.root
    }

    private fun setUp() {
        val lm = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        bookMarkAdapter = HomePageAdapters(bookMarkResults, requireContext(), this, 1)
        binding.bookmarksRecyclerview.layoutManager = lm
        binding.bookmarksRecyclerview.adapter = bookMarkAdapter
        binding.clearAll.setOnClickListener { v ->
            val alertDialog =
                AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Please Confirm")
            alertDialog.setMessage("Are you sure you want to delete all of them")
            alertDialog.setNegativeButton(
                "No"
            ) { dialog: DialogInterface?, which: Int -> }
            alertDialog.setPositiveButton(
                "Yes"
            ) { d: DialogInterface?, w: Int ->
                bookMarkViewModel.clearBookMarks()
                bmObject.listener.onAllCleared(bookMarkResults!!.size)
            }
            alertDialog.create()
            alertDialog.show()
        }
        binding.backButton.setOnClickListener { v -> closeFragment() }
        bookMarkViewModel.bookMarkResults.observe(viewLifecycleOwner) { results ->
            bookMarkResults!!.clear()
            bookMarkResults!!.addAll(results)
            bookMarkAdapter.notifyDataSetChanged()
            val flag=bookMarkResults.size==0
            binding.clearAll.isVisible=!flag
            binding.bookmarksRecyclerview.isVisible=!flag
            binding.noBookmarksLayout.isVisible=flag

        }
        bookMarkViewModel.msg.observe(viewLifecycleOwner,{
            if(it!=null && !TextUtils.isEmpty(it)){
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }

    override fun onItemClick(result: Result?) {
        if(result!=null) {
            val action: NavDirections =
                BookMarkFragmentDirections.actionBookMarkFragmentToDetailFragment(result)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    override fun onBookMarkClicked(result: Result?, position: Int, listener: BookMarkListener?) {
        bookMarkViewModel.setBookMarkStatus(result, position, listener)
        bmObject.listener.onItemChanged(result)
    }
}