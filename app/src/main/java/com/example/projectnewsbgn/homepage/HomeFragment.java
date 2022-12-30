package com.example.projectnewsbgn.homepage;

import static android.content.Context.MODE_PRIVATE;
import static com.example.projectnewsbgn.homepage.MainActivity.SHARED_PREFS_FETCH;
import static com.example.projectnewsbgn.homepage.MainActivity.TIME;
import static com.example.projectnewsbgn.login.UserAccessActivity.COUNTRY;
import static com.example.projectnewsbgn.login.UserAccessActivity.SHARED_PREFS_COUNTRY;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsHomeAdapter;
import com.example.projectnewsbgn.Interface.OnFetchDataListener;
import com.example.projectnewsbgn.Interface.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.NewsApiResponse;

import java.sql.Time;
import java.util.List;


public class HomeFragment extends Fragment implements SelectListener {

    RecyclerView recyclerView;
    NewsHomeAdapter newsRecyclerViewAdapter;
    ProgressDialog dialog;
    String country;

    long timePassedFromFetch;

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

        country = loadSavedCountry();

        /* Se non sono passati almeno 20 secondi da quando si ha eseguito la fetch se entro nella home non esegue la fetch,
        * senza repository la schermata viene distrutta e si perdono le news, ma funziona.
        * Ovvero se cambio pagina e ritorno sulla home non ricarico le news a meno che non sia passato un tempo X (in questo caso 20 secondi)
        * dall'ultima chiamata.
        * Commentato per il momento per evitare noie durante il test */

        /*timePassedFromFetch = calculateTimeFromFetch();*/

        if((timePassedFromFetch ==0) || (System.currentTimeMillis() - timePassedFromFetch > 20000) ){
            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Fetching news...");
            dialog.show();
            RequestManager manager = new RequestManager(getContext());
            manager.getNewsHeadlines(listener, "general", null,country);
        }

    }

    private long calculateTimeFromFetch() {

        long time;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_FETCH,MODE_PRIVATE);
        time = sharedPreferences.getLong(String.valueOf(TIME),0);
        return time;
    }

    private String loadSavedCountry() {

        String savedCountry;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_COUNTRY,MODE_PRIVATE);
        savedCountry = sharedPreferences.getString(COUNTRY,"");
        return savedCountry;
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<News> data, String message) {
            dialog.dismiss();
            if (data.isEmpty()) {
                Toast.makeText(getContext(), "No Data Found!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
            else{
                showNews(data);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<News> newsList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        newsRecyclerViewAdapter = new NewsHomeAdapter(getContext(), newsList,this);
        recyclerView.setAdapter(newsRecyclerViewAdapter);

    }

    @Override
    public void OnNewsClicked(News news) {
        Intent goToNews = new Intent(getActivity(), FullDisplayNewsActivity.class).putExtra("news",news);
        startActivity(goToNews);
        getActivity().finish();
    }
}