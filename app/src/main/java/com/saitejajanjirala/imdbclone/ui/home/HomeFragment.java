package com.saitejajanjirala.imdbclone.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saitejajanjirala.imdbclone.BaseApplication;
import com.saitejajanjirala.imdbclone.adapters.HomePageAdapters;
import com.saitejajanjirala.imdbclone.databinding.HomeFragmentBinding;
import com.saitejajanjirala.imdbclone.di.component.DaggerFragmentComponent;
import com.saitejajanjirala.imdbclone.di.module.FragmentModule;
import com.saitejajanjirala.imdbclone.listeners.BookMarkListener;
import com.saitejajanjirala.imdbclone.listeners.BookMarkStatusesListener;
import com.saitejajanjirala.imdbclone.listeners.listenerobject.BookMarkListenerObject;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.utils.Constants;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements HomePageAdapters.onHomePageItemClickListener, BookMarkStatusesListener {

    @Inject
    HomeViewModel mViewModel;

    HomePageAdapters nowPlayingAdapter, popularAdapter;
    List<Result> nowPlayingList;
    List<Result> popularList;
    HomeFragmentBinding binding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder()
                .applicationComponent(
                        ((BaseApplication) (requireContext().getApplicationContext())).applicationComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);

        nowPlayingList = new ArrayList<>();
        popularList = new ArrayList<>();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        initRootView();
        setUpObservers();
        return binding.getRoot();
    }

    private void initRootView() {
        LinearLayoutManager l1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager l2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.nowPlayingRecyclerview.setLayoutManager(l1);
        binding.popularRecyclerview.setLayoutManager(l2);
        nowPlayingAdapter = new HomePageAdapters(nowPlayingList, requireContext(), this, 0);
        popularAdapter = new HomePageAdapters(popularList, requireContext(), this, 0);
        binding.nowPlayingRecyclerview.setAdapter(nowPlayingAdapter);
        binding.popularRecyclerview.setAdapter(popularAdapter);
        binding.nowPlayingRecyclerview.setNestedScrollingEnabled(false);
        binding.popularRecyclerview.setNestedScrollingEnabled(false);

    }

    private void setUpObservers() {
        binding.searchView.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToSearchFragment();
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
        mViewModel.nowPlayingMutableLiveData.observe(getViewLifecycleOwner(), results -> {
            nowPlayingList.clear();
            nowPlayingList.addAll(results);
            nowPlayingAdapter.notifyDataSetChanged();
            if(nowPlayingList.size()!=0)
            {
                binding.homeScroll.setVisibility(View.VISIBLE);
                binding.noResultsLayout.setVisibility(View.GONE);
            }
            else{
                binding.homeScroll.setVisibility(View.GONE);
                binding.noResultsLayout.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.popularMutableLiveData.observe(getViewLifecycleOwner(), results -> {
            popularList.clear();
            popularList.addAll(results);
            popularAdapter.notifyDataSetChanged();
            if(popularList.size()!=0)
            {
                binding.homeScroll.setVisibility(View.VISIBLE);
                binding.noResultsLayout.setVisibility(View.GONE);
            }
            else{
                binding.homeScroll.setVisibility(View.GONE);
                binding.noResultsLayout.setVisibility(View.VISIBLE);
            }

        });
        binding.bookMarks.setOnClickListener(v -> {
            BookMarkListenerObject bookMarkListenerObject=new BookMarkListenerObject(this);
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToBookMarkFragment(bookMarkListenerObject);
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
        binding.retryButton.setOnClickListener(v->{
            mViewModel.getData();
        });
    }

    @Override
    public void onItemClick(Result result) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(result);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onBookMarkClicked(Result result, int position, BookMarkListener listener) {
        mViewModel.setBookMarkStatus(result, position, listener);
    }


    @Override
    public void onItemChanged(Result result) {
        String type=result.type;
        Log.i("itemchange",result.toString());
        int i=-1;
        if(type.equals(Constants.NOW_PLAYING)){
            for(Result res:nowPlayingList){
                if(res.id==result.id){
                    i=nowPlayingList.indexOf(res);
                    break;
                }
            }
            if(i!=-1) {
                Result res=nowPlayingList.get(i);
                res.bookmarked = !res.bookmarked;
                nowPlayingList.set(i, res);
                nowPlayingAdapter.notifyItemChanged(i);
            }
        }
        else{
            for(Result res:popularList){
                if(res.id==result.id){
                    i=popularList.indexOf(res);
                    break;
                }
            }
            if(i!=-1) {
                Result res=popularList.get(i);
                res.bookmarked = !res.bookmarked;
                popularList.set(i, res);
                popularAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onAllCleared(int size) {
        Log.i("cleared",size+"");
        if(size>0){
            for(Result i:popularList){
                i.bookmarked=false;
            }
            for(Result i:nowPlayingList){
                i.bookmarked=false;
            }
            popularAdapter.notifyDataSetChanged();
            nowPlayingAdapter.notifyDataSetChanged();
        }
    }
}