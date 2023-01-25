package com.example.projectnewsbgn.UI.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.squareup.picasso.Picasso;


public class FullNewsFragment extends Fragment {

    private News news;
    private TextView text_title,text_date,text_content,text_author,text_link;
    private ImageView iconNews;
    private ImageButton bntFav;
    private NewsViewModel newsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        Bundle bundle = getArguments();
        news = (News) bundle.getSerializable("full_news");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text_title = view.findViewById(R.id.titleFullNews);
        text_date = view.findViewById(R.id.dateFullNews);
        text_content = view.findViewById(R.id.contentFullNews);
        text_author = view.findViewById(R.id.linkNews);
        iconNews = view.findViewById(R.id.iconFullNews);
        text_link = view.findViewById(R.id.linkNews);
        bntFav = view.findViewById(R.id.btnFavourite);

        /*news = (News) getIntent().getSerializableExtra("news");*/

        text_title.setText(news.getTitle());
        text_date.setText(news.getPublishedAt());
        text_content.setText(news.getContent());
        text_author.setText(news.getAuthor());
        text_link.setText(news.getUrl());
        Picasso.get().load(news.getUrlToImage()).into(iconNews);

        text_author.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
            startActivity(browserIntent);
        });

        bntFav.setOnClickListener(v -> {
            newsViewModel.updateNewsNotSaved(news);
        });
    }
}