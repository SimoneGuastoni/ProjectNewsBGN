package com.example.projectnewsbgn.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectnewsbgn.R;

public class FavouritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*RecyclerView recyclerView = view.findViewById(R.id.container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<News> newsArray = new ArrayList<>();

        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(newsArray, new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onNewsClick(News news) {

            }

            @Override
            public void onDeleteButtonPressed(int position) {

            }

            @Override
            public void onFavButtonPressed(int adapterPosition) {

            }

            @Override
            public void onShareButtonPressed(int adapterPosition) {

            }
        });*/



    }

}