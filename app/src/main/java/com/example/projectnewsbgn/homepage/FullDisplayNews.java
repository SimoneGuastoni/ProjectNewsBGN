package com.example.projectnewsbgn.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.squareup.picasso.Picasso;

public class FullDisplayNews extends AppCompatActivity {

    News news;
    TextView text_title,text_date,text_content;
    ImageView iconNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_display_news);

        text_title = findViewById(R.id.titleFullNews);
        text_date = findViewById(R.id.dateFullNews);
        text_content = findViewById(R.id.contentFullNews);
        iconNews = findViewById(R.id.iconFullNews);

        news = (News) getIntent().getSerializableExtra("news");

        text_title.setText(news.getTitle());
        text_date.setText(news.getPublishedAt());
        text_content.setText(news.getContent());
        Picasso.get().load(news.getUrlToImage()).into(iconNews);
    }
}