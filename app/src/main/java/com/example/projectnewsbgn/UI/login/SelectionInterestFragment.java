package com.example.projectnewsbgn.UI.login;

import static android.content.Context.MODE_PRIVATE;
import static com.example.projectnewsbgn.UI.login.UserAccessActivity.COUNTRY;
import static com.example.projectnewsbgn.UI.login.UserAccessActivity.SHARED_PREFS;
import static com.example.projectnewsbgn.UI.login.UserAccessActivity.SHARED_PREFS_COUNTRY;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

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

import com.example.projectnewsbgn.UI.homepage.MainActivity;
import com.example.projectnewsbgn.R;

public class SelectionInterestFragment extends Fragment {

    private TextView selectCountryTxt,selectTopicsTxt;
    private Button completeRegistrationBtn;
    private Spinner countrySpinner;
    private CheckBox btnTopic1,btnTopic2,btnTopic3,btnTopic4,btnTopic5,btnTopic6;
    private String name,email,psw,country;
    private Boolean topic1 = false ,topic2 = false ,topic3 = false,
            topic4 = false,topic5 = false,topic6 = false, remember= false;

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
                Boolean checked = bundle.getBoolean("booleankey");
                remember= checked;
            }
        });

        Activity act = getActivity();

        selectCountryTxt = act.findViewById(R.id.selectCountryTxt);
        selectTopicsTxt = act.findViewById(R.id.selectInterestTxt);
        completeRegistrationBtn = act.findViewById(R.id.completeRegisterBtn);
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

            if (btnTopic1.isChecked() || btnTopic2.isChecked() || btnTopic3.isChecked() ||
                    btnTopic4.isChecked() || btnTopic5.isChecked() || btnTopic6.isChecked() ){
                country = countrySpinner.toString();
                if (btnTopic1.isChecked())
                    topic1=true;
                else
                    topic1=false;
                if (btnTopic2.isChecked())
                    topic2=true;
                else
                    topic2=false;
                if (btnTopic3.isChecked())
                    topic3=true;
                else
                    topic3= false;
                if (btnTopic4.isChecked())
                    topic4=true;
                else
                    topic4= false;
                if (btnTopic5.isChecked())
                    topic5=true;
                else
                    topic5= false;
                if (btnTopic6.isChecked())
                    topic6=true;
                else
                    topic6= false;

                Toast.makeText(getActivity(), "You've been successfully registered",
                        Toast.LENGTH_SHORT).show();

                Intent goToHome = new Intent(getActivity(), MainActivity.class);

                saveCountry(countrySpinner.getSelectedItem().toString());

                if(remember) {
                    Toast.makeText(getActivity(), "remember true", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = act.getSharedPreferences
                            (SHARED_PREFS, getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "true");
                    editor.commit();
                }
                startActivity(goToHome);
                getActivity().finish();
            }
            else {
                selectTopicsTxt.setText(R.string.notificationTopics);
                selectTopicsTxt.setError("");
            }
        });
    }

    private void saveCountry(String selectedCountry) {

        String countryId;

        switch (selectedCountry){
            case "Italy" :
                countryId = "it";
                break;
            case "France" :
                countryId = "fr";
                break;
            case "England" :
                countryId = "gb";
                break;
            default: countryId = "it";
        }

        SharedPreferences sharedPreferences = getActivity().
                getSharedPreferences(SHARED_PREFS_COUNTRY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY,countryId);
        editor.apply();
    }
}