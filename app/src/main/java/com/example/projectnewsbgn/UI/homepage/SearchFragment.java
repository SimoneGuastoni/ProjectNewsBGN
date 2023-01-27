package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsSearchAdapter;
import com.example.projectnewsbgn.Listener.SearchListener;
import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchListener {

    private NewsViewModel newsViewModel;
    private MutableLiveData<Result> newsObtained,accountObtained;
    private FullNewsFragment fullNewsFragment;
    private RecyclerView recyclerView;
    private NewsSearchAdapter newsSmallAdapter;
    private AccountViewModel accountViewModel;
    private ImageView businessTopic,scienceTopic,generalTopic,
            healthTopic,sportTopic,entertainmentTopic,technologyTopic,waitingImage,internetError;
    private ProgressBar progressBar;
    private String category,country,query;
    private TextView hintText;
    private SearchView searchView;
    private List<News> newsList;
    private List<String> allTopic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        newsList = new ArrayList<>();
        fullNewsFragment = new FullNewsFragment();

        country="general";
        query="";
        allTopic = new ArrayList<String>();
        allTopic.add("general");
        allTopic.add("sport");
        allTopic.add("health");
        allTopic.add("science");
        allTopic.add("business");
        allTopic.add("entertainment");
        allTopic.add("technology");

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
        hintText = view.findViewById(R.id.hintText);
        internetError = view.findViewById(R.id.iconInternetError);

        accountObtained = accountViewModel.getAccountData();
        accountObtained.observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()){
                country = ((Result.AccountSuccess) result).getData().getCountry();

            }
            else{
                Toast.makeText(getActivity(), "Error retrieve account data", Toast.LENGTH_SHORT).show();
            }

        });
        /*country = loadSavedCountry();*/

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsSmallAdapter = new NewsSearchAdapter(getContext(), newsList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsSmallAdapter);

        // Fetch eseguita con una specifica query inserita nella ricerca
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                waitingImage.setVisibility(View.GONE);
                hintText.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
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
        newsViewModel.updateNewsNotSaved(news);
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
                Toast.makeText(getContext(), result.getClass().toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                /*internetError.setVisibility(View.VISIBLE);*/
            }
        });
    }
}