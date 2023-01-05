package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsHomeAdapter;
import com.example.projectnewsbgn.Adapter.NewsSmallAdapter;
import com.example.projectnewsbgn.Interface.OnFetchDataListener;
import com.example.projectnewsbgn.Interface.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Repository.INewsRepository;
import com.example.projectnewsbgn.Repository.NewsRepository;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;
import com.example.projectnewsbgn.Utility.RequestManager;
import com.example.projectnewsbgn.Utility.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SelectListener, ResponseCallback {

    INewsRepository iNewsRepository;
    RecyclerView recyclerView;
    NewsSmallAdapter newsSmallAdapter;
    ImageView businessTopic,scienceTopic,generalTopic,healthTopic,sportTopic,entertainmentTopic,technologyTopic;
    ProgressBar progressBar;
    String category = "general",country;
    SearchView searchView;
    List<News> newsList;
    long timePassedFromFetch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iNewsRepository = new NewsRepository(requireActivity().getApplication(),this);
        newsList = new ArrayList<>();
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

        searchView = view.findViewById(R.id.searchBar);

        timePassedFromFetch = calculateTimeFromFetch();

        country = loadSavedCountry();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsSmallAdapter = new NewsSmallAdapter(getContext(), newsList, this, iNewsRepository);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsSmallAdapter);

        iNewsRepository.fetchNews(country,0,0,category,"");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        businessTopic.setOnClickListener(v -> {
            category = "business";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

        healthTopic.setOnClickListener(v -> {
            category = "health";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

        sportTopic.setOnClickListener(v -> {
            category = "sport";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

        entertainmentTopic.setOnClickListener(v -> {
            category = "entertainment";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

        technologyTopic.setOnClickListener(v -> {
            category = "technology";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

        generalTopic.setOnClickListener(v -> {
            category = "general";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

        scienceTopic.setOnClickListener(v -> {
            category = "science";
            progressBar.setVisibility(View.VISIBLE);
            iNewsRepository.fetchNews(country,0,timePassedFromFetch,category,"");
        });

    }

    @Override
    public void OnNewsClicked(News news) {
        Intent goToNews = new Intent(getActivity(), FullDisplayNewsActivity.class).putExtra("news",news);
        startActivity(goToNews);
        getActivity().finish();
    }

    @Override
    public void onSuccess(List<News> newsList, long lastUpdate) {
        if(newsList != null){
            this.newsList.clear();
            this.newsList.addAll(newsList);
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsSmallAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onNewsFavoriteStatusChange(News news) {

    }

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
}