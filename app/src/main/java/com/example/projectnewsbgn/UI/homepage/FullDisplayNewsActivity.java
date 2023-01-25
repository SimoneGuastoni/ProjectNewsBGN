package com.example.projectnewsbgn.UI.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.squareup.picasso.Picasso;

public class FullDisplayNewsActivity extends AppCompatActivity {

    private News news;
    private TextView text_title,text_date,text_content,text_author,text_link;
    private ImageView iconNews;
    private ImageButton bntFav;
    private NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_display_news);


        text_title = findViewById(R.id.titleFullNews);
        text_date = findViewById(R.id.dateFullNews);
        text_content = findViewById(R.id.contentFullNews);
        text_author = findViewById(R.id.linkNews);
        iconNews = findViewById(R.id.iconFullNews);
        text_link = findViewById(R.id.linkNews);
        bntFav = findViewById(R.id.btnFavourite);

        news = (News) getIntent().getSerializableExtra("news");

        text_title.setText(news.getTitle());
        text_date.setText(news.getPublishedAt());
        text_content.setText(news.getContent());
        text_author.setText(news.getAuthor());
        Picasso.get().load(news.getUrlToImage()).into(iconNews);

        text_author.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(news.getUrl()));
            startActivity(browserIntent);
        });

        bntFav.setOnClickListener(v -> {
            newsViewModel.updateNewsNotSaved(news);
        });
    }
}