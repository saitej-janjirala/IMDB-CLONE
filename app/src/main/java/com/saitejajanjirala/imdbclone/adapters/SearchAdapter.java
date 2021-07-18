package com.saitejajanjirala.imdbclone.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.imdbclone.databinding.ResultItemBinding;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private onSearchResultClickListener listener;
    private List<Result> results;

    public SearchAdapter(onSearchResultClickListener listener, List<Result> results) {
        this.listener = listener;
        this.results = results;
    }

    @NonNull
    @NotNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ResultItemBinding binding=ResultItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchViewHolder holder, int position) {
        if (position!=RecyclerView.NO_POSITION){
            holder.bindData(results.get(position));
            holder.itemView.setOnClickListener(v->{
                if(listener!=null){
                    listener.onResultClick(results.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.i("results size",results.size()+"");
        return results.size();

    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        ResultItemBinding binding;
        public SearchViewHolder(@NonNull @NotNull ResultItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        void bindData(Result result){
            if(result!=null){
                Utils.LoadImage(binding.getRoot().getContext(),result.poster_path,binding.resultImage);
                binding.rating.setText(result.vote_average+"");
                binding.title.setText(result.original_title);
            }
        }
    }
    public interface onSearchResultClickListener{
        void onResultClick(Result result);
    }
}
