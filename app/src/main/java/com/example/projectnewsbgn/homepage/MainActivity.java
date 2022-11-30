package com.example.projectnewsbgn.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.login.LoginActivity;
import com.example.projectnewsbgn.object.Account;

public class MainActivity extends AppCompatActivity {

    private Button logOut;
    public static final String SHARED_PREFS ="SharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOut = findViewById(R.id.logoutBtn);


        Account user = getIntent().getParcelableExtra("finalUser");

        SharedPreferences sharedPreferences2 = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.putString("name","true");
        editor.apply();


        logOut.setOnClickListener(view -> {
            changeRemember();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void changeRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name","false");
        editor.apply();
    }

}