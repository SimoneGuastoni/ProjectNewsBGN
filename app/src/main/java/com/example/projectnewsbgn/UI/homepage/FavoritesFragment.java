package com.example.projectnewsbgn.UI.homepage;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsSmallAdapter;
import com.example.projectnewsbgn.Listener.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.Main.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements SelectListener{

    private MutableLiveData<Result> newsObtained;

    RecyclerView recyclerViewFav;
    List<News> newsFavList;
    NewsViewModel newsViewModel;
    NewsSmallAdapter newsSmallAdapter;
    Button buttonDeleteAll;
    ImageView iconNoFavNews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

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

        newsSmallAdapter = new NewsSmallAdapter(getContext(),newsFavList,this);

        recyclerViewFav.setLayoutManager(layoutManager);
        recyclerViewFav.setAdapter(newsSmallAdapter);

        newsObtained = newsViewModel.getAllFavNews();
        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                int initialSize = this.newsFavList.size();
                this.newsFavList.clear();
                this.newsFavList.addAll(((Result.Success) result).getData().getNewsList());
                newsSmallAdapter.notifyItemRangeInserted(initialSize,this.newsFavList.size());
            } else {
                Toast.makeText(getContext(), "No favorite news yet", Toast.LENGTH_SHORT).show();
                iconNoFavNews.setVisibility(View.VISIBLE);
            }
        });

        buttonDeleteAll.setOnClickListener(v -> {
          for (int i=0 ; i<newsFavList.size(); i++){
              newsFavList.remove(i);
              newsSmallAdapter.notifyItemRemoved(i);
              onDeleteButtonPressed(newsFavList.get(i));
          }
        });
    }

    // Metodi del comportamenteo dell'adatper

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

    @Override
    public void onDeleteButtonPressed(News news) {
        newsViewModel.updateNews(news);
    }
}