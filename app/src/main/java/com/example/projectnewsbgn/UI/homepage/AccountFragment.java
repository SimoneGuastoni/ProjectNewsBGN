package com.example.projectnewsbgn.UI.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Repository.INewsRepository;
import com.example.projectnewsbgn.Repository.NewsRepository;
import com.example.projectnewsbgn.Utility.ResponseCallback;

import java.util.List;

public class AccountFragment extends Fragment implements ResponseCallback {

    TextView favouriteArticlesTot;
    INewsRepository iNewsRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iNewsRepository =new NewsRepository(requireActivity().getApplication(),this);

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

        iNewsRepository.getFavouriteNews();

    }

    @Override
    public void onSuccess(List<News> newsList, long lastUpdate) {
        favouriteArticlesTot.setText(String.valueOf(newsList.size()));
    }

    @Override
    public void onFailure(String errorMessage) {
        favouriteArticlesTot.setText(errorMessage);
    }

    @Override
    public void onNewsFavoriteStatusChange(News news) {

    }
}