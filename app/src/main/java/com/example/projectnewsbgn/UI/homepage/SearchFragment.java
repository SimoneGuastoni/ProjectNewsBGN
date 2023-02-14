package com.example.projectnewsbgn.UI.homepage;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.projectnewsbgn.Adapter.NewsSearchAdapter;
import com.example.projectnewsbgn.Listener.SearchListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchListener {

    private NewsViewModel newsViewModel;
    private MutableLiveData<Result> newsObtained;
    private FullNewsFragment fullNewsFragment;
    private RecyclerView recyclerView;
    private NewsSearchAdapter newsSmallAdapter;
    private AccountViewModel accountViewModel;
    private ImageView waitingImage;
    private ImageView internetError;
    private ProgressBar progressBar;
    private String category,country,query,language;
    private TextView hintText;
    private List<News> newsList;
    private List<String> allTopic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        newsList = new ArrayList<>();
        fullNewsFragment = new FullNewsFragment();


        query=getString(R.string.DefaultQuery);
        allTopic = new ArrayList<>();
        allTopic.add(getString(R.string.MustHaveTopic));
        allTopic.add(getString(R.string.SportTopic));
        allTopic.add(getString(R.string.HealthTopic));
        allTopic.add(getString(R.string.ScienceTopic));
        allTopic.add(getString(R.string.BusinessTopic));
        allTopic.add(getString(R.string.EntertainmentTopic));
        allTopic.add(getString(R.string.TechnologyTopic));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.SearchTitle);

        recyclerView = view.findViewById(R.id.recyclerViewSearch);
        progressBar = view.findViewById(R.id.progressBar);
        ImageView businessTopic = view.findViewById(R.id.businessTopic);
        ImageView scienceTopic = view.findViewById(R.id.scienceTopic);
        ImageView technologyTopic = view.findViewById(R.id.technologyTopic);
        ImageView generalTopic = view.findViewById(R.id.generalTopic);
        ImageView entertainmentTopic = view.findViewById(R.id.entertainmentTopic);
        ImageView healthTopic = view.findViewById(R.id.healthTopic);
        ImageView sportTopic = view.findViewById(R.id.sportTopic);
        waitingImage = view.findViewById(R.id.waitForInputImage);
        SearchView searchView = view.findViewById(R.id.searchBar);
        hintText = view.findViewById(R.id.hintText);
        internetError = view.findViewById(R.id.iconInternetError);

        internetError.setVisibility(View.GONE);

        MutableLiveData<Result> accountObtained = accountViewModel.getAccountData();
        accountObtained.observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()){
                country = ((Result.AccountSuccess) result).getData().getCountry();

            }
            else{
                Snackbar.make(requireView(),((Result.Error)result).getMessage(), Snackbar.LENGTH_SHORT).show();
                internetError.setVisibility(View.VISIBLE);
            }

        });

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsSmallAdapter = new NewsSearchAdapter(getContext(), newsList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsSmallAdapter);

        // Fetch eseguita con una specifica query inserita nella ricerca
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                language = calculateLanguage(country);
                waitingImage.setVisibility(View.GONE);
                hintText.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                newsObtained = newsViewModel.getNews(language,allTopic,query);
                rebuildNewsList(newsObtained);
                /*if (newsList.size()==0){
                    Snackbar.make(requireView(), "Nothing found", Snackbar.LENGTH_SHORT).show();
                }*/
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // Serie di fetch eseguite su di uno specifico topic a seconda del bottone

        businessTopic.setOnClickListener(v -> {
            category = "business";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        healthTopic.setOnClickListener(v -> {
            category = "health";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        sportTopic.setOnClickListener(v -> {
            category = "sport";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        entertainmentTopic.setOnClickListener(v -> {
            category = "entertainment";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        technologyTopic.setOnClickListener(v -> {
            category = "technology";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        generalTopic.setOnClickListener(v -> {
            category = "general";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        scienceTopic.setOnClickListener(v -> {
            category = "science";
            recyclerView.setVisibility(View.GONE);
            waitingImage.setVisibility(View.GONE);
            hintText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

    }

    private String calculateLanguage(String country) {
        String language;
        switch (country){
            case "it" : {
                language = "it";
                break;
            }
            case "fr" : {
                language = "fr";
                break;
            }
            case "gb" : {
                language = "en";
                break;
            }
            default: {
                language = "it";
            }
        }
        return language;
    }

    // Metodi del comportamento dell'adapter

    @Override
    public void OnNewsClicked(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("full_news",news);
        fullNewsFragment.setArguments(bundle);
        Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_fullNewsFragment,bundle);
    }

    @Override
    public void onFavButtonPressed(News news) {

        newsViewModel.updateNewsNotSaved(news).observe(getViewLifecycleOwner(), result -> {
            if(!result.isSuccess()){
                Snackbar.make(requireView(), R.string.ErrorFav2,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onShareButtonPressed(News news) {
        Intent shareLink = new Intent();
        shareLink.setAction(Intent.ACTION_SEND);
        shareLink.putExtra(Intent.EXTRA_TEXT, news.getUrl());
        shareLink.setType("text/plain");
        startActivity(shareLink);
    }


    // Metodi di supporto al fragment

    private void rebuildNewsList(MutableLiveData<Result> newsObtained) {
        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                int initialSize = this.newsList.size();
                this.newsList.clear();
                this.newsList.addAll(((Result.NewsSuccess) result).getData().getNewsList());
                newsSmallAdapter.notifyItemRangeChanged(initialSize,this.newsList.size());
                newsSmallAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                Snackbar.make(requireView(),((Result.Error)result).getMessage(), Snackbar.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                internetError.setVisibility(View.VISIBLE);
            }
        });
    }
}