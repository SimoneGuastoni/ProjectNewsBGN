package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.projectnewsbgn.Adapter.NewsHomeAdapter;
import com.example.projectnewsbgn.Listener.HomeListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.Main.NewsViewModel;
import com.example.projectnewsbgn.UI.Main.NewsViewModelFactory;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;
import com.example.projectnewsbgn.Utility.ServiceLocator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeListener {

    /* controllo */
    private MutableLiveData<Result> newsObtained;

    RecyclerView recyclerView;

    INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    NewsViewModel newsViewModel;

    /* Test multi fetch multitopics*/
    List<String> topicList = new ArrayList<String>();

    NewsHomeAdapter newsRecyclerViewAdapter;
    ProgressBar progressBar;
    String country;
    List<News> newsList;
    ImageView internetError;
    long timePassedFromFetch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsRepositoryWithLiveData = ServiceLocator.getInstance().getNewsRepository(
                requireActivity().getApplication());

        newsViewModel = new ViewModelProvider(
                requireActivity(),new NewsViewModelFactory(newsRepositoryWithLiveData))
                .get(NewsViewModel.class);

        newsList = new ArrayList<>();

        /* Test multiTopic */
        topicList.add("general");
        topicList.add("health");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        country = loadSavedCountry();

        timePassedFromFetch = calculateTimeFromFetch();

        progressBar = view.findViewById(R.id.progressBar);
        internetError = view.findViewById(R.id.iconInternetError);

        recyclerView = view.findViewById(R.id.RecyclerViewcontainer);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsRecyclerViewAdapter = new NewsHomeAdapter(getContext(), newsList,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);


        progressBar.setVisibility(View.VISIBLE);
        internetError.setVisibility(View.INVISIBLE);

        /* controllo */
        newsObtained = newsViewModel.getNews(country,topicList,timePassedFromFetch);

        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                int initialSize = this.newsList.size();
                this.newsList.clear();
                this.newsList.addAll(((Result.Success) result).getData().getNewsList());
                newsRecyclerViewAdapter.notifyItemRangeInserted(initialSize,this.newsList.size());
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), "Error 666", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
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
        newsViewModel.updateNews(news);
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
}