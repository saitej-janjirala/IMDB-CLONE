package com.saitejajanjirala.imdbclonekotlin.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.imdbclonekotlin.databinding.GenreItemBinding
import java.util.ArrayList

class GenreAdapter(private val list: ArrayList<String>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding: GenreItemBinding =
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bindData(list[position])
        }
    }

    override fun getItemCount()=list.size


    inner class GenreViewHolder(binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val binding: GenreItemBinding = binding
        fun bindData(genre: String?) {
            binding.genreText.text = genre
        }

    }
}