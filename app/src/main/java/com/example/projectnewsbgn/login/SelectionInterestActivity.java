package com.example.projectnewsbgn.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectnewsbgn.homepage.MainActivity;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.object.Account;

public class SelectionInterestActivity extends AppCompatActivity {

    private TextView selectCountryTxt,selectTopicsTxt;
    private Button completeRegistrationBtn;
    private Spinner countrySpinner;
    private CheckBox btnTopic1,btnTopic2,btnTopic3;
    private String name,email,psw,country;
    private Boolean topic1 = false ,topic2 = false ,topic3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_interest);

        Account account = getIntent().getParcelableExtra("user");


        /*Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("name");
            email = extras.getString("email");
            psw = extras.getString("psw");
        }*/

        selectCountryTxt = findViewById(R.id.selectCountryTxt);
        selectTopicsTxt = findViewById(R.id.selectInterestTxt);
        completeRegistrationBtn = findViewById(R.id.completeRegisterBtn);
        countrySpinner = findViewById(R.id.spinnerCountry);
        btnTopic1 = findViewById(R.id.toggleBtnTopic1);
        btnTopic2 = findViewById(R.id.toggleBtnTopic2);
        btnTopic3 = findViewById(R.id.toggleBtnTopidc3);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.CountryList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        completeRegistrationBtn.setOnClickListener(view -> {
            if (btnTopic1.isChecked() || btnTopic2.isChecked() || btnTopic3.isChecked()){
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
                Toast.makeText(this, "You've been successfully registered", Toast.LENGTH_SHORT).show();
                account.setTopic1(topic1);
                account.setTopic2(topic2);
                account.setTopic3(topic3);
                account.setCountry(country);
                Intent goToHome = new Intent(SelectionInterestActivity.this, MainActivity.class);
                goToHome.putExtra("finalUser",account);
                startActivity(goToHome);
                finish();
            }
            else {
                selectTopicsTxt.setText(R.string.notificationTopics);
                selectTopicsTxt.setError("");
            }
        });
    }
}