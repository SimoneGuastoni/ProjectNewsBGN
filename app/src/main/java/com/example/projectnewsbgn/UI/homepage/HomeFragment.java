package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.projectnewsbgn.Adapter.NewsHomeAdapter;
import com.example.projectnewsbgn.Listener.HomeListener;
import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.AccountRepository.IAccountRepositoryWithLiveData;
import com.example.projectnewsbgn.Repository.NewsRepository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.example.projectnewsbgn.UI.login.AccountViewModelFactory;
import com.example.projectnewsbgn.Utility.ServiceLocator;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeListener {

    private MutableLiveData<Result> newsObtained;
    private final FullNewsFragment fullNewsFragment = new FullNewsFragment();
    private RecyclerView recyclerView;
    private NewsViewModel newsViewModel;
    private List<String> topicList;
    private NewsHomeAdapter newsRecyclerViewAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String country;
    private Account account;
    private List<News> newsList;
    private ImageView internetError;
    private AccountViewModel accountViewModel;
    private  long timePassedFromFetch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IAccountRepositoryWithLiveData accountRepository = ServiceLocator.getInstance().getAccountRepository();

        accountViewModel = new ViewModelProvider(requireActivity(),
                new AccountViewModelFactory(accountRepository)).get(AccountViewModel.class);

        INewsRepositoryWithLiveData newsRepositoryWithLiveData = ServiceLocator.getInstance().getNewsRepository(
                requireActivity().getApplication());

        newsViewModel = new ViewModelProvider(
                requireActivity(),new NewsViewModelFactory(newsRepositoryWithLiveData,requireActivity().getApplication()))
                .get(NewsViewModel.class);

        newsList = new ArrayList<>();

        topicList = new ArrayList<>();

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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.AppTitle);

        progressBar = view.findViewById(R.id.progressBar);
        internetError = view.findViewById(R.id.iconInternetError);
        recyclerView = view.findViewById(R.id.RecyclerViewcontainer);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        timePassedFromFetch = calculateTimeFromFetch();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsRecyclerViewAdapter = new NewsHomeAdapter(getContext(), newsList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);

        progressBar.setVisibility(View.VISIBLE);
        internetError.setVisibility(View.GONE);

        MutableLiveData<Result> accountDataObtained = accountViewModel.getAccountData();
        accountDataObtained.observe(getViewLifecycleOwner(), resultAccount -> {
            if(resultAccount.isSuccess()){
                account = ((Result.AccountSuccess) resultAccount).getData();
                country = account.getCountry();
                topicList = account.getFavAccountTopics();
                if (country != null && topicList.size() != 0) {

                    newsObtained = newsViewModel.getNews(country, topicList, timePassedFromFetch);

                    newsObtained.observe(getViewLifecycleOwner(), resultNewsCall -> {
                        if (resultNewsCall.isSuccess()) {
                            int initialSize = HomeFragment.this.newsList.size();
                            rebuildList(initialSize, newsRecyclerViewAdapter,recyclerView,resultNewsCall);
                            progressBar.setVisibility(View.INVISIBLE);
                            internetError.setVisibility(View.GONE);
                        } else {
                            Snackbar.make(view,((Result.Error)resultNewsCall).getMessage(),Snackbar.LENGTH_SHORT).show();
                            recyclerView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.INVISIBLE);
                            internetError.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
            else{
                newsViewModel.getNewsFromDatabase(timePassedFromFetch)
                        .observe(getViewLifecycleOwner(), resultFromLocal -> {
                    if(resultFromLocal.isSuccess()){
                        int initialSize = HomeFragment.this.newsList.size();
                        rebuildList(initialSize, newsRecyclerViewAdapter,recyclerView,resultFromLocal);
                        progressBar.setVisibility(View.INVISIBLE);
                        internetError.setVisibility(View.GONE);                    }
                    else {
                        Snackbar.make(view,((Result.Error)resultFromLocal).getMessage(),Snackbar.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        internetError.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }

            swipeRefreshLayout.setOnRefreshListener(() -> newsViewModel.getNews(country,topicList,0)
                    .observe(getViewLifecycleOwner(), resultRefresh -> {
                        if(resultRefresh.isSuccess()){
                            int initialSize = HomeFragment.this.newsList.size();
                            rebuildList(initialSize, newsRecyclerViewAdapter,recyclerView,resultRefresh);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            Snackbar.make(view,((Result.Error)resultRefresh).getMessage(),Snackbar.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
            }));

        });
    }

    private void rebuildList(int initialSize, NewsHomeAdapter newsRecyclerViewAdapter, RecyclerView recyclerView, Result result) {
        recyclerView.setVisibility(View.GONE);
        HomeFragment.this.newsList.clear();
        HomeFragment.this.newsList.addAll(((Result.NewsSuccess) result).getData().getNewsList());
        newsRecyclerViewAdapter.notifyItemRangeInserted(initialSize, HomeFragment.this.newsList.size());
        newsRecyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
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

        newsViewModel.updateNews(news).observe(getViewLifecycleOwner(), result -> {
            if(!result.isSuccess()){
                Snackbar.make(requireView(), result.getClass().toString(),Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onShareButtonPressed(News news) {
        Intent shareLink = new Intent();
        shareLink.setAction(Intent.ACTION_SEND);
        shareLink.putExtra(Intent.EXTRA_TEXT, news.getUrl());
        shareLink.setType("text/plain");
        startActivity(shareLink);
    }

    // Metodi di supporto al fragment

    private long calculateTimeFromFetch() {
        long time;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS_FETCH,MODE_PRIVATE);
        time = sharedPreferences.getLong(String.valueOf(MainActivity.TIME),0);
        return time;
    }
}