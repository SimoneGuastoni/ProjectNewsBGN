package com.example.projectnewsbgn.UI.homepage;

import android.content.Context;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SettingFragment extends Fragment {

    private Spinner countrySpinner;
    private TextInputEditText accountName,countryName;
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6;
    private Button updateBtn;
    private AccountViewModel accountViewModel;
    private MutableLiveData<Result> accountDataObtained;
    private List<String> topicList;
    private String accountId,copyEmail;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        topicList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO alternativa?
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        countrySpinner = view.findViewById(R.id.spinnerCountry);
        accountName = view.findViewById(R.id.accountName);
        countryName = view.findViewById(R.id.countryName);
        progressBar = view.findViewById(R.id.progressBar);
        updateBtn = view.findViewById(R.id.updateBtn);
        cb1 = view.findViewById(R.id.cbTopic1);
        cb2 = view.findViewById(R.id.cbTopic2);
        cb3 = view.findViewById(R.id.cbTopic3);
        cb4 = view.findViewById(R.id.cbTopic4);
        cb5 = view.findViewById(R.id.cbTopic5);
        cb6 = view.findViewById(R.id.cbTopic6);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.CountryList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        accountDataObtained = accountViewModel.getAccountData();
        accountDataObtained.observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                Account copyAccount = ((Result.AccountSuccess) result).getData();
                accountId = copyAccount.getId();
                String countryId = copyAccount.getCountry();
                countryName.setText(findCountryNameAndSetIcon(countryId,countryName));
                accountName.setText(copyAccount.getAccountName());
                copyEmail = copyAccount.getEmail();
                topicList.clear();
                topicList = ((Result.AccountSuccess) result).getData().getFavAccountTopics();
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
                Toast.makeText(getContext(), ((Result.Error)result).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        updateBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
           String newCountry,newName;
           List<String> newTopicList = new ArrayList<>();
           newTopicList.add("general");
            if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked() ||
                    cb4.isChecked() || cb5.isChecked() || cb6.isChecked() &&
                    !accountName.getText().toString().equals("")) {

                newName = accountName.getText().toString();
                newCountry = findCountryId(countrySpinner.getSelectedItem().toString());

                if (cb1.isChecked())
                    newTopicList.add(cb1.getText().toString().toLowerCase(Locale.ROOT));
                if (cb2.isChecked())
                    newTopicList.add(cb2.getText().toString().toLowerCase(Locale.ROOT));
                if (cb3.isChecked())
                    newTopicList.add(cb3.getText().toString().toLowerCase(Locale.ROOT));
                if (cb4.isChecked())
                    newTopicList.add(cb4.getText().toString().toLowerCase(Locale.ROOT));
                if (cb5.isChecked())
                    newTopicList.add(cb5.getText().toString().toLowerCase(Locale.ROOT));
                if (cb6.isChecked())
                    newTopicList.add(cb6.getText().toString().toLowerCase(Locale.ROOT));

                accountViewModel.changeAccountData(accountId,copyEmail,newName,newCountry,newTopicList)
                        .observe(getViewLifecycleOwner(), new Observer<Result>() {
                    @Override
                    public void onChanged(Result result) {
                        if (result.isSuccess()){
                            Navigation.findNavController(requireView())
                                    .navigate(R.id.action_settingFragment_to_accountFragment);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS_FETCH, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong(String.valueOf(MainActivity.TIME),0);
                            editor.apply();
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), ((Result.Error)result).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Compile all forms", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String findCountryId(String selectedCountry) {
        String countryId;

        switch (selectedCountry){
            case "Italy" : {
                countryId = "it";
                break;
            }
            case "France" : {
                countryId = "fr";
                break;
            }
            case "England" : {
                countryId = "gb";
                break;
            }
            default: {
                countryId = "it";
            }
        }
        return countryId;
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