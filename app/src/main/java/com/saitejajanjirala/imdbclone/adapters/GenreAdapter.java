package com.saitejajanjirala.imdbclone.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.imdbclone.databinding.GenreItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{

    private ArrayList<String> list;

    public GenreAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        GenreItemBinding binding=GenreItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new GenreViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GenreViewHolder holder, int position) {
        if(position!=RecyclerView.NO_POSITION){
            holder.bindData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        Log.i("saiteja",list.size()+"");
        return list.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder{
        private GenreItemBinding binding;
        public GenreViewHolder(@NonNull @NotNull GenreItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        void bindData(String genre){
            binding.genreText.setText(genre);
        }
    }
}
