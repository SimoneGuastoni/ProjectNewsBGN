package com.example.projectnewsbgn.UI.homepage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projectnewsbgn.Adapter.NewsFavAdapter;
import com.example.projectnewsbgn.Listener.FavListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoritesFragment extends Fragment implements FavListener {

    private FullNewsFragment fullNewsFragment;
    private RecyclerView recyclerViewFav;
    private List<News> newsFavList,controlList;
    private NewsViewModel newsViewModel;
    private NewsFavAdapter newsFavAdapter;
    private TextView hintText;
    private Button buttonDeleteAll;
    private ProgressBar progressBar;
    private ImageView iconNoFavNews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        newsFavList = new ArrayList<>();

        controlList = new ArrayList<>();

        fullNewsFragment = new FullNewsFragment();
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

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(requireActivity())).getSupportActionBar()).setTitle("Favorite");

        buttonDeleteAll = view.findViewById(R.id.btnDeleteAll);
        recyclerViewFav = view.findViewById(R.id.recyclerViewFav);
        iconNoFavNews = view.findViewById(R.id.noFavNews);
        progressBar = view.findViewById(R.id.progressBar);
        hintText = view.findViewById(R.id.hintText);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);

        newsFavAdapter = new NewsFavAdapter(newsFavList,this);
        recyclerViewFav.setLayoutManager(layoutManager);
        recyclerViewFav.setAdapter(newsFavAdapter);

        MutableLiveData<Result> newsObtained = newsViewModel.getAllFavNews();
        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                int initialSize = this.newsFavList.size();
                this.newsFavList.clear();
                this.newsFavList.addAll(((Result.NewsSuccess) result).getData().getNewsList());
                newsFavAdapter.notifyItemRangeInserted(initialSize,this.newsFavList.size());
                newsFavAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                iconNoFavNews.setVisibility(View.GONE);
                hintText.setVisibility(View.GONE);
                buttonDeleteAll.setVisibility(View.VISIBLE);
                recyclerViewFav.setVisibility(View.VISIBLE);
            } else {
                Snackbar.make(view, ((Result.Error)result).getMessage(), Snackbar.LENGTH_SHORT).show();
                buttonDeleteAll.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                iconNoFavNews.setVisibility(View.VISIBLE);
                hintText.setVisibility(View.VISIBLE);
            }
        });

        buttonDeleteAll.setOnClickListener(v -> {
            if(controlList.size()>0){
                int size = controlList.size();
                News controlNews;
                for (int i=size-1 ; i>-1; i--){
                    controlNews = controlList.get(i);
                    controlList.remove(i);
                    newsFavAdapter.notifyItemRemoved(newsFavList.indexOf(controlNews));
                    onDeleteButtonPressed(controlNews);
                    newsFavList.remove(controlNews);
                }
            }
            else {
                Snackbar.make(view, "Check at least one news or click the delete icon",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    // Metodi del comportamenteo dell'adatper

    @Override
    public void OnNewsClicked(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("full_news",news);
        fullNewsFragment.setArguments(bundle);
        Navigation.findNavController(requireView()).navigate(R.id.action_favouritesFragment_to_fullNewsFragment,bundle);
    }

    @Override
    public void onDeleteButtonPressed(News news) {
        newsViewModel.updateNews(news).observe(getViewLifecycleOwner(), result -> {
            if (!result.isSuccess()){
                Snackbar.make(requireView(),"Cancel error",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteNewsChecked(News news,Boolean checked) {
        if (checked){
            controlList.add(news);
        }
        else {
            controlList.remove(news);
        }
    }

    @Override
    public void onShareButtonPressed(News news) {
        Intent shareLink = new Intent();
        shareLink.setAction(Intent.ACTION_SEND);
        shareLink.putExtra(Intent.EXTRA_TEXT, news.getUrl());
        shareLink.setType("text/plain");
        startActivity(shareLink);
    }
}