package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projectnewsbgn.Adapter.NewsHomeAdapter;
import com.example.projectnewsbgn.Listener.HomeListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.NewsRepository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;
import com.example.projectnewsbgn.Utility.ServiceLocator;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeListener {

    private MutableLiveData<Result> newsObtained;
    private FullNewsFragment fullNewsFragment = new FullNewsFragment();
    private RecyclerView recyclerView;
    private INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    private NewsViewModel newsViewModel;
    /* Test multi fetch multitopics*/
    private List<String> topicList = new ArrayList<String>();
    private NewsHomeAdapter newsRecyclerViewAdapter;
    private ProgressBar progressBar;
    private String country;
    private List<News> newsList;
    private ImageView internetError;
    private  long timePassedFromFetch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsRepositoryWithLiveData = ServiceLocator.getInstance().getNewsRepository(
                requireActivity().getApplication());

        newsViewModel = new ViewModelProvider(
                requireActivity(),new NewsViewModelFactory(newsRepositoryWithLiveData,requireActivity().getApplication()))
                .get(NewsViewModel.class);

        newsList = new ArrayList<>();

        /* Test multiTopic */
        topicList.clear();
        topicList.add("business");
        topicList.add("entertainment");
        topicList.add("health");

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

        timePassedFromFetch = calculateTimeFromFetch();

        progressBar = view.findViewById(R.id.progressBar);
        internetError = view.findViewById(R.id.iconInternetError);

        recyclerView = view.findViewById(R.id.RecyclerViewcontainer);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsRecyclerViewAdapter = new NewsHomeAdapter(getContext(), newsList,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);



        progressBar.setVisibility(View.VISIBLE);
        internetError.setVisibility(View.INVISIBLE);

        newsObtained = newsViewModel.getNews(country,topicList,timePassedFromFetch);

        newsObtained.observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.isSuccess()) {
                    int initialSize = HomeFragment.this.newsList.size();
                    HomeFragment.this.newsList.clear();
                    HomeFragment.this.newsList.addAll(((Result.Success) result).getData().getNewsList());
                    newsRecyclerViewAdapter.notifyItemRangeInserted(initialSize, HomeFragment.this.newsList.size());
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(HomeFragment.this.getContext(), "Error 666", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    // Metodi del comportamento dell'adapter

    @Override
    public void OnNewsClicked(News news) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("full_news",news);
        fullNewsFragment.setArguments(bundle);
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_fullNewsFragment,bundle);
    }

    @Override
    public void onFavButtonPressed(News news) {
        newsViewModel.updateNews(news);
    }

    // Metodi di supporto al fragment

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