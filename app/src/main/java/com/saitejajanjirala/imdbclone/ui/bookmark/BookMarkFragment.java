package com.saitejajanjirala.imdbclone.ui.bookmark;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saitejajanjirala.imdbclone.BaseApplication;
import com.saitejajanjirala.imdbclone.R;
import com.saitejajanjirala.imdbclone.adapters.HomePageAdapters;
import com.saitejajanjirala.imdbclone.databinding.FragmentBookMarkBinding;
import com.saitejajanjirala.imdbclone.di.component.DaggerFragmentComponent;
import com.saitejajanjirala.imdbclone.di.module.FragmentModule;
import com.saitejajanjirala.imdbclone.listeners.listenerobject.BookMarkListenerObject;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;
import com.saitejajanjirala.imdbclone.ui.detail.DetailFragmentArgs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class BookMarkFragment extends Fragment implements HomePageAdapters.onHomePageItemClickListener {

    FragmentBookMarkBinding binding;
    @Inject
    BookMarkViewModel bookMarkViewModel;
    List<Result> bookMarkResults;
    HomePageAdapters bookMarkAdapter;
    BookMarkListenerObject bmObject;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder()
                .applicationComponent(
                        ((BaseApplication) (requireContext().getApplicationContext())).applicationComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
        bookMarkResults =new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_book_mark, container, false);
        binding=FragmentBookMarkBinding.bind(view);
        bmObject=BookMarkFragmentArgs.fromBundle(getArguments()).getBmobject();
        setUp();
        return binding.getRoot();
    }

    private void setUp(){
        StaggeredGridLayoutManager lm=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bookMarkAdapter=new HomePageAdapters(bookMarkResults,requireContext(),this,1);
        binding.bookmarksRecyclerview.setLayoutManager(lm);
        binding.bookmarksRecyclerview.setAdapter(bookMarkAdapter);
        binding.clearAll.setOnClickListener(v->{
            AlertDialog.Builder alertDialog =new AlertDialog.Builder(requireContext());
            alertDialog.setTitle("Please Confirm");
            alertDialog.setMessage("Are you sure you want to delete all of them");
            alertDialog.setNegativeButton("No", (dialog, which) -> {
            });
            alertDialog.setPositiveButton("Yes",(d,w)->{
                bookMarkViewModel.clearBookMarks();
                bmObject.listener.onAllCleared(bookMarkResults.size());

            });
            alertDialog.create();
            alertDialog.show();
        });
        binding.backButton.setOnClickListener(v->{
            closeFragment();
        });

        bookMarkViewModel.bookMarkResults.observe(getViewLifecycleOwner(), results -> {
            bookMarkResults.clear();
            bookMarkResults.addAll(results);
            bookMarkAdapter.notifyDataSetChanged();
            if(bookMarkResults.size()==0){
                binding.clearAll.setVisibility(View.GONE);
                binding.bookmarksRecyclerview.setVisibility(View.GONE);
                binding.noBookmarksLayout.setVisibility(View.VISIBLE);
            }
            else{
                binding.clearAll.setVisibility(View.VISIBLE);
                binding.bookmarksRecyclerview.setVisibility(View.VISIBLE);
                binding.noBookmarksLayout.setVisibility(View.GONE);
            }
        });

    }
    private void closeFragment(){
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }

    @Override
    public void onItemClick(Result result) {
        NavDirections action = BookMarkFragmentDirections.actionBookMarkFragmentToDetailFragment(result);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onBookMarkClicked(Result result, int position, BookMarkListener listener) {
        bookMarkViewModel.setBookMarkStatus(result, position, listener);
        bmObject.listener.onItemChanged(result);
    }
}