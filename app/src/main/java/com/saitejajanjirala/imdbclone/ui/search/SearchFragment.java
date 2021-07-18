package com.saitejajanjirala.imdbclone.ui.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.imdbclone.BaseApplication;
import com.saitejajanjirala.imdbclone.R;
import com.saitejajanjirala.imdbclone.adapters.SearchAdapter;
import com.saitejajanjirala.imdbclone.databinding.FragmentSearchBinding;
import com.saitejajanjirala.imdbclone.di.component.DaggerFragmentComponent;
import com.saitejajanjirala.imdbclone.di.module.FragmentModule;
import com.saitejajanjirala.imdbclone.models.Result;
import com.saitejajanjirala.imdbclone.models.SearchResult;
import com.saitejajanjirala.imdbclone.ui.home.HomeFragmentDirections;
import com.saitejajanjirala.imdbclone.utils.RxSearchObservable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements SearchAdapter.onSearchResultClickListener {

    FragmentSearchBinding binding;
    @Inject
    SearchViewModel searchViewModel;
    ArrayList<Result> results;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder()
                .applicationComponent(
                        ((BaseApplication) (requireContext().getApplicationContext())).applicationComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
        results = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.bind(inflater.inflate(R.layout.fragment_search, container, false));
        bindView();
        return binding.getRoot();
    }

    private void bindView() {
        LinearLayoutManager lm = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.searchResults.setLayoutManager(lm);
        SearchAdapter adapter = new SearchAdapter(this, results);
        binding.searchResults.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL);
        searchViewModel.searchResultMutableLiveData.observe(getViewLifecycleOwner(), new Observer<SearchResult>() {
            @Override
            public void onChanged(SearchResult searchResult) {
                if (searchResult != null) {
                    if(searchResult.results.isEmpty()){
                        binding.searchResults.setVisibility(View.GONE);
                        binding.noResultsLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        binding.searchResults.setVisibility(View.VISIBLE);
                        binding.noResultsLayout.setVisibility(View.GONE);
                    }
                    results.clear();
                    results.addAll(searchResult.results);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        binding.backButton.setOnClickListener(v -> {
            closeFragment();
        });
        RxSearchObservable.fromView(binding.searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Throwable {
                        searchViewModel.getData(s);
                        return Observable.just("called");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {

                    }
                });

    }

    @Override
    public void onResultClick(Result result) {
        NavDirections action=SearchFragmentDirections.actionSearchFragmentToDetailFragment(result);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    private void closeFragment() {
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }

}