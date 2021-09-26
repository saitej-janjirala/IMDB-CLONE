package com.saitejajanjirala.imdbclonekotlin.adapters

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.imdbclonekotlin.listeners.BookMarkListener
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.saitejajanjirala.imdbclonekotlin.R
import com.saitejajanjirala.imdbclonekotlin.databinding.BookMarkedItemBinding
import com.saitejajanjirala.imdbclonekotlin.databinding.HomePageItemBinding
import com.saitejajanjirala.imdbclonekotlin.utils.Helper
import com.saitejajanjirala.imdbclonekotlin.models.Result
class HomePageAdapters(
    private val results: MutableList<Result>,
    private val context: Context,
    private val listener: onHomePageItemClickListener?,
    val type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BookMarkListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding: HomePageItemBinding =
                HomePageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HomePageViewHolder(binding)
        } else {
            Log.i("bookmark viewholder", "bookmark")
            val binding: BookMarkedItemBinding =
                BookMarkedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BookMarkViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            if (holder is HomePageViewHolder) {
                val result = results[position]
                holder.bindData(result!!)
            } else if (holder is BookMarkViewHolder) {
                Log.i("bookmark binding", "called")
                val result = results[position]
                holder.bindData(result!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBookMarkClicked(position: Int, result: Result?) {
        results[position] = result!!
        notifyItemChanged(position)
    }

    inner class HomePageViewHolder(binding: HomePageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: HomePageItemBinding
        fun bindData(result: Result) {
            result.poster_path?.let { Helper.loadImage(context, it, binding.imdbPoster) }
            if (result.adult) {
                binding.eighteenPlus.visibility = View.VISIBLE
            }
            binding.title.text = result.title
            if (result.release_date != null) {
                binding.year.text = "${result.release_date}"
            } else {
                binding.year.text = "-"
            }
            if (result.bookmarked) {
                binding.bookMark.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.bookmark)
                )
            } else {
                binding.bookMark.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.un_bookmark)
                )
            }
            binding.rating.text = result.vote_average.toString() + ""
        }

        init {
            this.binding = binding
            binding.imdbPoster.setOnClickListener { v ->
                val position = this@HomePageViewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(results[position])
                }
            }
            binding.bookMark.setOnClickListener { v ->
                val position = this@HomePageViewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onBookMarkClicked(results[position], position, this@HomePageAdapters)
                }
            }
        }
    }

    inner class BookMarkViewHolder(binding: BookMarkedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: BookMarkedItemBinding = binding
        fun bindData(result: Result) {
            result.poster_path?.let { Helper.loadImage(context, it, binding.imdbPoster) }
            if (result.adult) {
                binding.eighteenPlus.visibility = View.VISIBLE
            }
            binding.title.text = result.title
            if (result.release_date != null) {
                binding.year.text = "${result.release_date}"
            } else {
                binding.year.text = "-"
            }
            if (result.bookmarked) {
                binding.bookMark.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.bookmark)
                )
            } else {
                binding.bookMark.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.un_bookmark)
                )
            }
            binding.rating.text = result.vote_average.toString() + ""
        }

        init {
            binding.imdbPoster.setOnClickListener { v ->
                val position = this@BookMarkViewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(results[position])
                }
            }
            binding.bookMark.setOnClickListener { v ->
                val position = this@BookMarkViewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onBookMarkClicked(results[position], position, this@HomePageAdapters)
                }
            }
        }
    }

    interface onHomePageItemClickListener {
        fun onItemClick(result: Result?)
        fun onBookMarkClicked(result: Result?, position: Int, listener: BookMarkListener?)
    }
}