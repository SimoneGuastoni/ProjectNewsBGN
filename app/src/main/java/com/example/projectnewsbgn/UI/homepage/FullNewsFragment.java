package com.example.projectnewsbgn.UI.homepage;

import android.content.Intent;
import android.graphics.Color;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.squareup.picasso.Picasso;


public class FullNewsFragment extends Fragment {

    private News news;
    private ImageButton btnFav;
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("NewsBGN");

        TextView text_title = view.findViewById(R.id.titleFullNews);
        TextView text_date = view.findViewById(R.id.dateFullNews);
        TextView text_content = view.findViewById(R.id.contentFullNews);
        ImageView iconNews = view.findViewById(R.id.iconFullNews);
        TextView text_link = view.findViewById(R.id.linkNews);
        btnFav = view.findViewById(R.id.btnFavourite);
        ImageButton btnShare = view.findViewById(R.id.btnShare);

        text_title.setText(news.getTitle());
        text_date.setText(news.getPublishedAt());
        text_content.setText(news.getContent());
        text_link.setText(news.getUrl());
        text_link.setTextColor(Color.BLUE);
        Picasso.get().load(news.getUrlToImage()).into(iconNews);

        String text = text_content.getText().toString();
        text = text.replaceAll("\\[\\+\\w+\\s+chars]", getString(R.string.FullNewsAdvice));
        text_content.setText(text);

        if (news.getFavourite()){
            btnFav.setImageDrawable(AppCompatResources.getDrawable
                    (requireContext(),R.drawable.ic_baseline_favorite_24));
        }

        text_link.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
            startActivity(browserIntent);
        });

        btnFav.setOnClickListener(v -> {
            changeLikeIcon(btnFav,news.getFavourite());
            btnFav.refreshDrawableState();
            newsViewModel.updateNewsNotSaved(news);

        });

        btnShare.setOnClickListener(v -> {
            Intent shareLink = new Intent();
            shareLink.setAction(Intent.ACTION_SEND);
            shareLink.putExtra(Intent.EXTRA_TEXT, news.getUrl());
            shareLink.setType("text/plain");
            startActivity(shareLink);
        });
    }

    private void changeLikeIcon(ImageButton btnFav, boolean favourite) {
        if(favourite){
            btnFav.setImageDrawable(AppCompatResources.getDrawable
                    (requireContext(),R.drawable.ic_baseline_favorite_border_24));
        }
        else {
            btnFav.setImageDrawable(AppCompatResources.getDrawable
                    (requireContext(),R.drawable.ic_baseline_favorite_24));
        }
    }
}