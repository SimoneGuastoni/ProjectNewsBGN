package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsFavAdapter;
import com.example.projectnewsbgn.Adapter.NewsHomeAdapter;
import com.example.projectnewsbgn.Repository.INewsRepository;
import com.example.projectnewsbgn.Interface.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.NewsRepository;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Utility.ResponseCallback;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements SelectListener, ResponseCallback {

    RecyclerView recyclerView;
    INewsRepository iNewsRepository;
    NewsFavAdapter newsRecyclerViewAdapter;
    ProgressDialog dialog;
    String country;
    List<News> newsList;
    long timePassedFromFetch,time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iNewsRepository =new NewsRepository(requireActivity().getApplication(),this);

        newsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        country = loadSavedCountry();

        /* Se non sono passati almeno 20 secondi da quando si ha eseguito la fetch se entro nella home non esegue la fetch,
        * senza repository la schermata viene distrutta e si perdono le news, ma funziona.
        * Ovvero se cambio pagina e ritorno sulla home non ricarico le news a meno che non sia passato un tempo X (in questo caso 20 secondi)
        * dall'ultima chiamata.
        * Commentato per il momento per evitare noie durante il test */

        timePassedFromFetch = calculateTimeFromFetch();

        recyclerView = view.findViewById(R.id.RecyclerViewcontainer);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);

        newsRecyclerViewAdapter = new NewsFavAdapter(getContext(),newsList,this,iNewsRepository );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);


        if((timePassedFromFetch == 0) || (System.currentTimeMillis() - timePassedFromFetch > 20000) ){

            /* Shared pref che mi permette di calcolare il tempo tra le fetch*/
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS_FETCH, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            time = System.currentTimeMillis();
            editor.putLong(String.valueOf(MainActivity.TIME),time);
            editor.apply();

            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Fetching news...");
            dialog.show();

            iNewsRepository.fetchNews(country,0,timePassedFromFetch);
        }
        else{
            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Retrieve from Database");
            dialog.show();
            iNewsRepository.fetchNews(country,0,timePassedFromFetch);
        }

    }

    @Override
    public void OnNewsClicked(News news) {
        Intent goToNews = new Intent(getActivity(), FullDisplayNewsActivity.class).putExtra("news",news);
        startActivity(goToNews);
        getActivity().finish();
    }

    @Override
    public void onSuccess(List<News> newsList, long lastUpdate) {
        if(newsList != null){
            this.newsList.clear();
            this.newsList.addAll(newsList);
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsRecyclerViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getContext(), "Errore1234", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onNewsFavoriteStatusChange(News news) {

    }

    private long calculateTimeFromFetch() {

        long time;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS_FETCH,MODE_PRIVATE);
        time = sharedPreferences.getLong(String.valueOf(MainActivity.TIME),0);
        return time;
    }

    private String loadSavedCountry() {

        String savedCountry;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(UserAccessActivity.SHARED_PREFS_COUNTRY,MODE_PRIVATE);
        savedCountry = sharedPreferences.getString(UserAccessActivity.COUNTRY,"");
        return savedCountry;
    }
}