package com.example.projectnewsbgn.UI.homepage;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.Repository.AccountReposiroty.IAccountRepositoryWithLiveData;
import com.example.projectnewsbgn.Repository.NewsRepository.INewsRepositoryWithLiveData;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.example.projectnewsbgn.UI.login.AccountViewModelFactory;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;
import com.example.projectnewsbgn.Utility.ServiceLocator;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeListener {

    private MutableLiveData<Result> newsObtained,accountDataObtained;
    private FullNewsFragment fullNewsFragment = new FullNewsFragment();
    private RecyclerView recyclerView;
    private INewsRepositoryWithLiveData newsRepositoryWithLiveData;
    private NewsViewModel newsViewModel;
    private List<String> topicList;
    private NewsHomeAdapter newsRecyclerViewAdapter;
    private ProgressBar progressBar;
    private String country;
    private Account account;
    private List<News> newsList;
    private ImageView internetError;
    private AccountViewModel accountViewModel;
    private IAccountRepositoryWithLiveData accountRepository;
    private  long timePassedFromFetch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountRepository = ServiceLocator.getInstance().getAccountRepository
                (requireActivity().getApplication());

        accountViewModel = new ViewModelProvider(requireActivity(),
                new AccountViewModelFactory(accountRepository)).get(AccountViewModel.class);

        newsRepositoryWithLiveData = ServiceLocator.getInstance().getNewsRepository(
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("NewsBGN");

        progressBar = view.findViewById(R.id.progressBar);
        internetError = view.findViewById(R.id.iconInternetError);
        recyclerView = view.findViewById(R.id.RecyclerViewcontainer);

        timePassedFromFetch = calculateTimeFromFetch();

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        newsRecyclerViewAdapter = new NewsHomeAdapter(getContext(), newsList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsRecyclerViewAdapter);

        progressBar.setVisibility(View.VISIBLE);
        internetError.setVisibility(View.INVISIBLE);



        accountDataObtained = accountViewModel.getAccountData();
        accountDataObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                account = ((Result.AccountSuccess) result).getData();
                country = account.getCountry();
                topicList = account.getFavAccountTopics();
                if (country != null && topicList.size() != 0) {

                    newsObtained = newsViewModel.getNews(country, topicList, timePassedFromFetch);

                    newsObtained.observe(getViewLifecycleOwner(), result2 -> {
                        if (result2.isSuccess()) {
                            int initialSize = HomeFragment.this.newsList.size();
                            HomeFragment.this.newsList.clear();
                            HomeFragment.this.newsList.addAll(((Result.NewsSuccess) result2).getData().getNewsList());
                            newsRecyclerViewAdapter.notifyItemRangeInserted(initialSize, HomeFragment.this.newsList.size());
                            newsRecyclerViewAdapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(HomeFragment.this.getContext(), result2.getClass().toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            internetError.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
            else{
                Toast.makeText(getContext(), result.getClass().toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
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

    //TODO risolvere riciclo della recycler view che segna graficamente il like quando scorri le notizie
    @Override
    public void onFavButtonPressed(News news) {

        newsViewModel.updateNews(news).observe(getViewLifecycleOwner(), result -> {
            if(!result.isSuccess()){
                Toast.makeText(getContext(), result.getClass().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Metodi di supporto al fragment

    private long calculateTimeFromFetch() {
        long time;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS_FETCH,MODE_PRIVATE);
        time = sharedPreferences.getLong(String.valueOf(MainActivity.TIME),0);
        return time;
    }
}