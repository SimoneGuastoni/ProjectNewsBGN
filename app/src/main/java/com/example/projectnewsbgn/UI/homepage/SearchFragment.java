package com.example.projectnewsbgn.UI.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsSmallAdapter;
import com.example.projectnewsbgn.Interface.OnFetchDataListener;
import com.example.projectnewsbgn.Interface.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Utility.RequestManager;

import java.util.List;

public class SearchFragment extends Fragment implements SelectListener {

    RecyclerView recyclerView;
    NewsSmallAdapter newsSmallAdapter;
    ImageView businessTopic,scienceTopic,generalTopic,healthTopic,sportTopic,entertainmentTopic,technologyTopic;
    ProgressDialog dialog;
    String category = "general",country;
    SearchView searchView;
    Spinner countrySpinner;

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

        businessTopic = view.findViewById(R.id.businessTopic);
        scienceTopic = view.findViewById(R.id.scienceTopic);
        technologyTopic = view.findViewById(R.id.technologyTopic);
        generalTopic = view.findViewById(R.id.generalTopic);
        entertainmentTopic = view.findViewById(R.id.entertainmentTopic);
        healthTopic = view.findViewById(R.id.healthTopic);
        sportTopic = view.findViewById(R.id.sportTopic);

        countrySpinner = view.findViewById(R.id.spinnerCountrySearch);

        searchView = view.findViewById(R.id.searchBar);

        country = getContext().getString(R.string.countryAccount);

        dialog = new ProgressDialog(getContext());
        RequestManager manager = new RequestManager(getContext());
        manager.getNewsHeadlines(listener, "general", null,country);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.CountryList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String countrySelected = countrySpinner.getSelectedItem().toString();
                if (countrySelected.equals("Italy")){
                    country = "it";
                    manager.getNewsHeadlines(listener,"general",null,country);
                }
                if (countrySelected.equals("France")){
                    country = "fr";
                    manager.getNewsHeadlines(listener,"general",null,country);
                }
                if (countrySelected.equals("England")){
                    country = "gb";
                    manager.getNewsHeadlines(listener,"general",null,country);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Search of:" + query);
                dialog.show();
                RequestManager queryManager = new RequestManager(getContext());
                queryManager.getNewsHeadlines(listener,category,query,country);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        businessTopic.setOnClickListener(v -> {
            category = "business";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

        healthTopic.setOnClickListener(v -> {
            category = "health";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

        sportTopic.setOnClickListener(v -> {
            category = "sport";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

        entertainmentTopic.setOnClickListener(v -> {
            category = "entertainment";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

        technologyTopic.setOnClickListener(v -> {
            category = "technology";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

        generalTopic.setOnClickListener(v -> {
            category = "general";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

        scienceTopic.setOnClickListener(v -> {
            category = "science";
            dialog.setTitle("Fetching news about:" + category);
            dialog.show();
            RequestManager managerSearch = new RequestManager(getContext());
            managerSearch.getNewsHeadlines(listener, category, null,country);
        });

    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<News> data, String message) {
            if (data.isEmpty()) {
                Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
            else{
                showNews(data);
                dialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<News> newsList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        newsSmallAdapter = new NewsSmallAdapter(getContext(), newsList,this);
        recyclerView.setAdapter(newsSmallAdapter);

    }

    @Override
    public void OnNewsClicked(News news) {
        Intent goToNews = new Intent(getActivity(), FullDisplayNewsActivity.class).putExtra("news",news);
        startActivity(goToNews);
        getActivity().finish();
    }
}