package com.example.projectnewsbgn.UI.login;

import static android.content.Context.MODE_PRIVATE;
import static com.example.projectnewsbgn.UI.login.UserAccessActivity.SHARED_PREFS;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.UI.homepage.MainActivity;
import com.example.projectnewsbgn.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectionInterestFragment extends Fragment {

    private TextView selectTopicsTxt;
    private Button completeRegistrationBtn;
    private Spinner countrySpinner;
    private CheckBox btnTopic1,btnTopic2,btnTopic3,btnTopic4,btnTopic5,btnTopic6;
    private String name,email,psw,country;
    private boolean remember;
    private List<String> topicList;
    private AccountViewModel accountViewModel;
    private LinearProgressIndicator progressIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        accountViewModel.setAuthenticationError(false);
        topicList = new ArrayList<>();
        topicList.add("general");
    }

    public SelectionInterestFragment() {super(R.layout.fragment_selection_interest);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_selection_interest,container,false);
    }

    @Override
    public void onViewCreated(View onCreateView,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getSupportFragmentManager().setFragmentResultListener("bundleKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle bundle) {
                remember= bundle.getBoolean("booleankey");
                name = bundle.getString("namekey");
                email = bundle.getString("emailkey");
                psw = bundle.getString("pswkey");
            }
        });

        Activity act = getActivity();

        completeRegistrationBtn = act.findViewById(R.id.completeRegisterBtn);
        progressIndicator = act.findViewById(R.id.progressIndicator);
        selectTopicsTxt = act.findViewById(R.id.selectInterestTxt);
        countrySpinner = act.findViewById(R.id.spinnerCountry);
        btnTopic1 = act.findViewById(R.id.toggleBtnTopic1);
        btnTopic2 = act.findViewById(R.id.toggleBtnTopic2);
        btnTopic3 = act.findViewById(R.id.toggleBtnTopic3);
        btnTopic4 = act.findViewById(R.id.toggleBtnTopic4);
        btnTopic5 = act.findViewById(R.id.toggleBtnTopic5);
        btnTopic6 = act.findViewById(R.id.toggleBtnTopic6);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.CountryList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        completeRegistrationBtn.setOnClickListener(view -> {

            progressIndicator.setVisibility(View.VISIBLE);
            if (btnTopic1.isChecked() || btnTopic2.isChecked() || btnTopic3.isChecked() ||
                    btnTopic4.isChecked() || btnTopic5.isChecked() || btnTopic6.isChecked() ){

                country = countrySpinner.toString();

                if (btnTopic1.isChecked())
                    topicList.add(btnTopic1.getText().toString().toLowerCase(Locale.ROOT));
                if (btnTopic2.isChecked())
                    topicList.add(btnTopic2.getText().toString().toLowerCase(Locale.ROOT));
                if (btnTopic3.isChecked())
                    topicList.add(btnTopic3.getText().toString().toLowerCase(Locale.ROOT));
                if (btnTopic4.isChecked())
                    topicList.add(btnTopic4.getText().toString().toLowerCase(Locale.ROOT));
                if (btnTopic5.isChecked())
                    topicList.add(btnTopic5.getText().toString().toLowerCase(Locale.ROOT));
                if (btnTopic6.isChecked())
                    topicList.add(btnTopic6.getText().toString().toLowerCase(Locale.ROOT));

                Intent goToHome = new Intent(getActivity(), MainActivity.class);

                saveCountry(countrySpinner.getSelectedItem().toString());

                accountViewModel.authentication(name,email,psw,country,topicList)
                        .observe(getViewLifecycleOwner(), result -> {
                            if(result.isSuccess()){
                                if(remember) {
                                    Toast.makeText(getActivity(), "remember true", Toast.LENGTH_SHORT).show();
                                    Account account = ((Result.AccountSuccess) result).getData();
                                    SharedPreferences sharedPreferences = act.getSharedPreferences
                                            (SHARED_PREFS, getContext().MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", "true");
                                    editor.commit();
                                }
                                Toast.makeText(getActivity(), "You've been successfully registered",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(goToHome);
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(act, result.getClass().toString(), Toast.LENGTH_SHORT).show();
                                topicList.clear();
                                topicList.add("general");
                                progressIndicator.setVisibility(View.GONE);
                            }
                        });
            }
            else {
                progressIndicator.setVisibility(View.GONE);
                selectTopicsTxt.setText(R.string.notificationTopics);
                selectTopicsTxt.setError("");
            }
        });
    }

    private void saveCountry(String selectedCountry) {

        String countryId;

        switch (selectedCountry){
            case "Italy" : {
                countryId = "it";
                country = countryId;
                break;
            }
            case "France" : {
                countryId = "fr";
                country = countryId;
                break;
            }
            case "England" : {
                countryId = "gb";
                country = countryId;
                break;
            }
            default: {
                countryId = "it";
                country = countryId;
            }
        }
    }
}