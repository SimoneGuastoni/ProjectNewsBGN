package com.example.projectnewsbgn.Listener;

import com.example.projectnewsbgn.Models.News;

public interface HomeListener {

        void OnNewsClicked(News news);

        void onFavButtonPressed(News news);

}
