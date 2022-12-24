package com.example.projectnewsbgn.Interface;

import com.example.projectnewsbgn.Models.News;

import java.util.List;

public interface OnFetchDataListener<T>{

    void onFetchData(List<News> list, String message);
    void onError(String message);
}
