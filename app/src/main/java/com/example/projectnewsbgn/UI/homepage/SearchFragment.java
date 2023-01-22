package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsSmallAdapter;
import com.example.projectnewsbgn.Listener.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.Main.NewsViewModel;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SelectListener{

    NewsViewModel newsViewModel;
    MutableLiveData<Result> newsObtained;

    RecyclerView recyclerView;
    NewsSmallAdapter newsSmallAdapter;
    ImageView businessTopic,scienceTopic,generalTopic,healthTopic,sportTopic,entertainmentTopic,technologyTopic,waitingImage;
    ProgressBar progressBar;
    String category = "general",country,query = "";
    SearchView searchView;
    List<News> newsList;
    List<String> allTopic;
    long timePassedFromFetch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        newsList = new ArrayList<>();
        allTopic = new ArrayList<String>();
        allTopic.add("general");
        allTopic.add("sport");
        allTopic.add("health");
        allTopic.add("business");
        allTopic.add("technology");
        allTopic.add("entertainment");
        allTopic.add("science");
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

        recyclerView = view.findViewById(R.id.recyclerViewSearch);
        progressBar = view.findViewById(R.id.progressBar);

        businessTopic = view.findViewById(R.id.businessTopic);
        scienceTopic = view.findViewById(R.id.scienceTopic);
        technologyTopic = view.findViewById(R.id.technologyTopic);
        generalTopic = view.findViewById(R.id.generalTopic);
        entertainmentTopic = view.findViewById(R.id.entertainmentTopic);
        healthTopic = view.findViewById(R.id.healthTopic);
        sportTopic = view.findViewById(R.id.sportTopic);
        waitingImage = view.findViewById(R.id.waitForInputImage);

        searchView = view.findViewById(R.id.searchBar);

        timePassedFromFetch = calculateTimeFromFetch();

        country = loadSavedCountry();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsSmallAdapter = new NewsSmallAdapter(getContext(), newsList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsSmallAdapter);

        // Fetch eseguita con una specifica query inserita nella ricerca
        //TODO Sistemare fetch con barra di ricerca
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                waitingImage.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                newsObtained = newsViewModel.getNews(country,allTopic,query);
                rebuildNewsList(newsObtained);
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
            waitingImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        healthTopic.setOnClickListener(v -> {
            category = "health";
            waitingImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        sportTopic.setOnClickListener(v -> {
            category = "sport";
            waitingImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        entertainmentTopic.setOnClickListener(v -> {
            category = "entertainment";
            waitingImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        technologyTopic.setOnClickListener(v -> {
            category = "technology";
            progressBar.setVisibility(View.VISIBLE);
        });

        generalTopic.setOnClickListener(v -> {
            category = "general";
            waitingImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

        scienceTopic.setOnClickListener(v -> {
            category = "science";
            waitingImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            newsObtained = newsViewModel.getNews(country,category,query);
            rebuildNewsList(newsObtained);
        });

    }

    // Metodi del comportamento dell'adapter

    @Override
    public void OnNewsClicked(News news) {
        Intent goToNews = new Intent(getActivity(), FullDisplayNewsActivity.class).putExtra("news",news);
        startActivity(goToNews);
        getActivity().finish();
    }

    @Override
    public void onFavButtonPressed(News news) {
        newsViewModel.updateNewsNotSaved(news);
    }

    @Override
    public void onDeleteButtonPressed(News news) {

    }

    // Metodi di supporto al fragment

    private long calculateTimeFromFetch() {
        long time;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS_FETCH,MODE_PRIVATE);
        time = sharedPreferences.getLong(String.valueOf(MainActivity.TIME),0);
        return time;
    }

    private String loadSavedCountry() {
        String savedCountry;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(UserAccessActivity.SHARED_PREFS_COUNTRY,MODE_PRIVATE);
        savedCountry = sharedPreferences.getString(UserAccessActivity.COUNTRY,"");
        return savedCountry;
    }


    private void rebuildNewsList(MutableLiveData<Result> newsObtained) {
        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                int initialSize = this.newsList.size();
                this.newsList.clear();
                this.newsList.addAll(((Result.Success) result).getData().getNewsList());
                newsSmallAdapter.notifyItemRangeChanged(initialSize,this.newsList.size());
                newsSmallAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}