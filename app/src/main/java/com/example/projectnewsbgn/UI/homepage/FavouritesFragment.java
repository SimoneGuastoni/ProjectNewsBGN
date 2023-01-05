package com.example.projectnewsbgn.UI.homepage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsSmallAdapter;
import com.example.projectnewsbgn.Interface.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Repository.INewsRepository;
import com.example.projectnewsbgn.Repository.NewsRepository;
import com.example.projectnewsbgn.Utility.ResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements SelectListener,ResponseCallback {

    RecyclerView recyclerViewFav;
    List<News> newsFavList;
    INewsRepository iNewsRepository;
    NewsSmallAdapter newsSmallAdapter;
    Button buttonDeleteAll;
    ImageView iconNoFavNews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iNewsRepository = new NewsRepository(requireActivity().getApplication(),this);

        newsFavList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewFav = view.findViewById(R.id.recyclerViewFav);
        iconNoFavNews = view.findViewById(R.id.noFavNews);
        buttonDeleteAll = view.findViewById(R.id.btnDeleteAll);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);

        newsSmallAdapter = new NewsSmallAdapter(getContext(),newsFavList,this,iNewsRepository);

        recyclerViewFav.setLayoutManager(layoutManager);
        recyclerViewFav.setAdapter(newsSmallAdapter);

        iNewsRepository.getFavouriteNews();

        buttonDeleteAll.setOnClickListener(v -> {
          iNewsRepository.deleteFavouriteNews();
        });
    }

    @Override
    public void onSuccess(List<News> newsList, long lastUpdate) {
        if(newsList.size() != 0){
            this.newsFavList.clear();
            this.newsFavList.addAll(newsList);
            if(newsList.size() != 0) {
                iconNoFavNews.setVisibility(View.INVISIBLE);
            }
        }
        else
            Toast.makeText(getContext(), "No Fav News...", Toast.LENGTH_SHORT).show();
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsSmallAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailure(String errorMessage) {
        iconNoFavNews.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNewsFavoriteStatusChange(News news) {

    }

    @Override
    public void OnNewsClicked(News news) {
        Intent goToNews = new Intent(getActivity(), FullDisplayNewsActivity.class).putExtra("news",news);
        startActivity(goToNews);
        getActivity().finish();
    }
}