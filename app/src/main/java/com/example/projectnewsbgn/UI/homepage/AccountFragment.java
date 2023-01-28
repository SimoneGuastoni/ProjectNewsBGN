package com.example.projectnewsbgn.UI.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountFragment extends Fragment{

    private MutableLiveData<Result> newsObtained;

    private TextView favouriteArticlesTot,accountNameTxt;
    private TextInputEditText name,email,country;
    private NewsViewModel newsViewModel;
    private List<News> newsFavList;
    private MutableLiveData<Result> accountDataObtained;
    private int empty = 0;
    private Button btnUpdate;
    private List<String> topicList;
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6;
    private AccountViewModel accountViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        newsFavList = new ArrayList<>();
        topicList = new ArrayList<>();
    }

    //TODO sncakbar al posto delle toast
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Account");

        favouriteArticlesTot = view.findViewById(R.id.numberFavouriteArticles);
        accountNameTxt = view.findViewById(R.id.accountNametxt);
        btnUpdate = view.findViewById(R.id.updateBtn);
        country = view.findViewById(R.id.countryTxtEdit);
        email = view.findViewById(R.id.emailTxtEdit);
        name = view.findViewById(R.id.nameTxtEdit);
        cb1 = view.findViewById(R.id.toggleBtnTopic1);
        cb2 = view.findViewById(R.id.toggleBtnTopic2);
        cb3 = view.findViewById(R.id.toggleBtnTopic3);
        cb4 = view.findViewById(R.id.toggleBtnTopic4);
        cb5 = view.findViewById(R.id.toggleBtnTopic5);
        cb6 = view.findViewById(R.id.toggleBtnTopic6);


        newsObtained = newsViewModel.getAllFavNews();
        newsObtained.observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                this.newsFavList.clear();
                this.newsFavList.addAll(((Result.NewsSuccess) result).getData().getNewsList());
                favouriteArticlesTot.setText(String.valueOf(newsFavList.size()));
            } else {
                favouriteArticlesTot.setText(String.valueOf(empty));
            }
        });

        accountDataObtained = accountViewModel.getAccountData();
        accountDataObtained.observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                Account copyAccount = ((Result.AccountSuccess) result).getData();
                String countryId = copyAccount.getCountry();
                country.setText(findCountryNameAndSetIcon(countryId,country));
                accountNameTxt.setText(copyAccount.getAccountName());
                name.setText(copyAccount.getAccountName());
                email.setText(copyAccount.getEmail());
                /*topicList.clear();*/
                topicList = ((Result.AccountSuccess) result).getData().getFavAccountTopics();
                clearCb(cb1,cb2,cb3,cb4,cb5,cb6);
                for(int i=0; i<topicList.size();i++){
                    if(topicList.get(i).equals(cb1.getText().toString().toLowerCase(Locale.ROOT))){
                        cb1.setChecked(true);
                    }
                    if(topicList.get(i).equals(cb2.getText().toString().toLowerCase(Locale.ROOT))){
                        cb2.setChecked(true);
                    }
                    if(topicList.get(i).equals(cb3.getText().toString().toLowerCase(Locale.ROOT))){
                        cb3.setChecked(true);
                    }
                    if(topicList.get(i).equals(cb4.getText().toString().toLowerCase(Locale.ROOT))){
                        cb4.setChecked(true);
                    }
                    if(topicList.get(i).equals(cb5.getText().toString().toLowerCase(Locale.ROOT))){
                        cb5.setChecked(true);
                    }
                    if(topicList.get(i).equals(cb6.getText().toString().toLowerCase(Locale.ROOT))){
                        cb6.setChecked(true);
                    }
                }

            } else {
                Toast.makeText(getContext(), result.getClass().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_accountFragment_to_settingFragment);
        });
    }

    private void clearCb(CheckBox cb1, CheckBox cb2, CheckBox cb3, CheckBox cb4, CheckBox cb5, CheckBox cb6) {
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        cb6.setChecked(false);
    }

    private String findCountryNameAndSetIcon(String countryId,TextInputEditText countryTxt) {
        String countryName = "";
        switch (countryId){
            case "it" : {
                countryName = "Italy";
                countryTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.italy,0,0,0);
                break;
            }
            case "fr" : {
                countryName = "France";
                countryTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.france,0,0,0);
                break;
            }
            case "gb" : {
                countryName = "United kingdom";
                countryTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.england,0,0,0);
                break;
            }

        }
        return countryName;
    }


}