package com.example.projectnewsbgn.Listener;

import com.example.projectnewsbgn.Models.News;

public interface FavListener {
    void OnNewsClicked(News news);

    void onFavButtonPressed(News news);

    void onDeleteButtonPressed(News news);

    void deleteNewsChecked(News news,Boolean checked);
}
