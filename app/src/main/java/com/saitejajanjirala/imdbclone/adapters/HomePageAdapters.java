package com.saitejajanjirala.imdbclone.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.imdbclone.R;

import com.saitejajanjirala.imdbclone.databinding.BookMarkedItemBinding;
import com.saitejajanjirala.imdbclone.databinding.HomePageItemBinding;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;
import com.saitejajanjirala.imdbclone.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomePageAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BookMarkListener {
    private List<Result> results;
    private Context context;
    private onHomePageItemClickListener listener;
    private int type;


    public HomePageAdapters(List<Result> results, Context context, onHomePageItemClickListener listener, int type) {
        this.results = results;
        this.context = context;
        this.listener = listener;
        this.type = type;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType==0){
            HomePageItemBinding binding=HomePageItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new HomePageViewHolder(binding);
        }
        else{
            Log.i("bookmark viewholder","bookmark");
            BookMarkedItemBinding binding=BookMarkedItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new BookMarkViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if(position!=RecyclerView.NO_POSITION) {
            if(holder instanceof HomePageViewHolder) {
                Result result = results.get(position);
                ((HomePageViewHolder) holder).bindData(result);
            }
            else if(holder instanceof BookMarkViewHolder){
                Log.i("bookmark binding","called");
                Result result = results.get(position);
                ((BookMarkViewHolder) holder).bindData(result);
            }
        }
    }



    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onBookMarkClicked(int position, Result result) {
        this.results.set(position, result);
        notifyItemChanged(position);
    }

    public class HomePageViewHolder extends RecyclerView.ViewHolder {
        HomePageItemBinding binding;
        public HomePageViewHolder(@NonNull @NotNull HomePageItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.imdbPoster.setOnClickListener(v -> {
                int position=HomePageViewHolder.this.getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    if(listener!=null){
                        listener.onItemClick(results.get(position));
                    }
                }
            });
            binding.bookMark.setOnClickListener(v->{
                int position=HomePageViewHolder.this.getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    if(listener!=null){
                        listener.onBookMarkClicked(results.get(position),position,HomePageAdapters.this);
                    }
                }
            });
        }

        void bindData(Result result) {
            Utils.LoadImage(context,result.poster_path,binding.imdbPoster);
            if (result.adult) {
                binding.eighteenPlus.setVisibility(View.VISIBLE);
            }
            binding.title.setText(result.title);
            if(result.release_date!=null) {
                binding.year.setText(result.release_date.split("-")[0]);
            }
            else{
                binding.year.setText("-");
            }
            if(result.bookmarked){
                binding.bookMark.setImageDrawable(
                        ContextCompat.getDrawable(context,R.drawable.bookmark)
                );
            }
            else{
                binding.bookMark.setImageDrawable(
                        ContextCompat.getDrawable(context,R.drawable.un_bookmark)
                );
            }
            binding.rating.setText(result.vote_average+"");
        }
    }
    public class BookMarkViewHolder extends RecyclerView.ViewHolder {
        BookMarkedItemBinding binding;
        public BookMarkViewHolder(@NonNull @NotNull BookMarkedItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.imdbPoster.setOnClickListener(v -> {
                int position=BookMarkViewHolder.this.getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    if(listener!=null){
                        listener.onItemClick(results.get(position));
                    }
                }
            });
            binding.bookMark.setOnClickListener(v->{
                int position=BookMarkViewHolder.this.getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    if(listener!=null){
                        listener.onBookMarkClicked(results.get(position),position,HomePageAdapters.this);
                    }
                }
            });
        }

        void bindData(Result result) {
            Utils.LoadImage(context,result.poster_path,binding.imdbPoster);
            if (result.adult) {
                binding.eighteenPlus.setVisibility(View.VISIBLE);
            }
            binding.title.setText(result.title);
            if(result.release_date!=null) {
                binding.year.setText(result.release_date.split("-")[0]);
            }
            else{
                binding.year.setText("-");
            }
            if(result.bookmarked){
                binding.bookMark.setImageDrawable(
                        ContextCompat.getDrawable(context,R.drawable.bookmark)
                );
            }
            else{
                binding.bookMark.setImageDrawable(
                        ContextCompat.getDrawable(context,R.drawable.un_bookmark)
                );
            }
            binding.rating.setText(result.vote_average+"");
        }
    }


    public interface onHomePageItemClickListener{
        void onItemClick(Result result);
        void onBookMarkClicked(Result result,int position,BookMarkListener listener);
    }

    public int getType() {
        return type;
    }
}
