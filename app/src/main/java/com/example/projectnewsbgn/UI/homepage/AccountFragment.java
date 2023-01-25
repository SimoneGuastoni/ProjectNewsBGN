package com.example.projectnewsbgn.UI.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment{

    private MutableLiveData<Result> newsObtained;

    private TextView favouriteArticlesTot;
    private NewsViewModel newsViewModel;
    private List<News> newsFavList;
    private int empty = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        newsFavList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favouriteArticlesTot = view.findViewById(R.id.numberFavouriteArticles);

        newsObtained = newsViewModel.getAllFavNews();
        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                this.newsFavList.clear();
                this.newsFavList.addAll(((Result.Success) result).getData().getNewsList());
                favouriteArticlesTot.setText(String.valueOf(newsFavList.size()));
            } else {
                favouriteArticlesTot.setText(String.valueOf(empty));
            }
        });
    }

}