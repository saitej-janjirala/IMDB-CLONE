package com.saitejajanjirala.imdbclone.ui.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.saitejajanjirala.imdbclone.R;
import com.saitejajanjirala.imdbclone.adapters.GenreAdapter;
import com.saitejajanjirala.imdbclone.databinding.FragmentDetailBinding;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class DetailFragment extends Fragment {

    FragmentDetailBinding binding;
    Result result;
    ArrayList<String> genres;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        genres=new ArrayList<>();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        binding=FragmentDetailBinding.bind(view);
        result=DetailFragmentArgs.fromBundle(getArguments()).getResult();
        bindView();
        return binding.getRoot();
    }

    private void bindView() {
        if(result!=null){
            Utils.LoadImage(requireContext(),result.backdrop_path,binding.coverImage);
            Utils.LoadImage(requireContext(),result.poster_path,binding.posterImage);
            genres.addAll(Utils.getGenresFromIds(result.genre_ids));
            binding.rating.setText(result.vote_average+"");
            binding.title.setText(result.title);
            binding.topTitle.setText(result.title);
            binding.overView.setText(result.overview);
            binding.voters.setText(result.vote_count+"");
        }
        FlexboxLayoutManager layoutManager=new FlexboxLayoutManager(requireContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.generesRecyclerview.setLayoutManager(layoutManager);

        GenreAdapter adapter=new GenreAdapter(genres);
        binding.generesRecyclerview.setAdapter(adapter);
        binding.backButton.setOnClickListener(v -> closeFragment());
    }
    private void closeFragment(){
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }


}