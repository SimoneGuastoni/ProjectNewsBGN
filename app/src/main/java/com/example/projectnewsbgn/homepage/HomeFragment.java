package com.example.projectnewsbgn.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsRecyclerViewAdapter;
import com.example.projectnewsbgn.Listeners.OnFetchDataListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.NewsApiResponse;
import com.example.projectnewsbgn.util.RequestManager;

import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    NewsRecyclerViewAdapter newsRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RecyclerViewcontainer);

       /* List<News> newsArray = new ArrayList<>();
        for(int i = 0; i < 100;i++){
            newsArray.add(new News());
        }

        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(newsArray, new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onNewsClick(News news) {
                Snackbar.make(view,news.getTitle(),Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteButtonPressed(int position) {
                Snackbar.make(view,"Size:" + newsArray.size(),Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFavButtonPressed(int adapterPosition) {
                Snackbar.make(view,"Add to favourite",Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onShareButtonPressed(int adapterPosition) {
                Snackbar.make(view,"Share news",Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);*/
        RequestManager manager = new RequestManager(requireContext());
        manager.getNews(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<News> data, String message) {
            showNews(data);
            if (data.isEmpty()) {
                Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getContext(), "Error1234", Toast.LENGTH_SHORT).show();
        }

        private void showNews(List<News> newsList) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getContext(), newsList);
            recyclerView.setAdapter(newsRecyclerViewAdapter);

        }
    };
}