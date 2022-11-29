package com.example.projectnewsbgn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email,psw;
    private CheckBox rememberCb;
    private Button loginBtn;
    private TextView forgotPswTxt,createAccountTxt;
    public static final String SHARED_PREFS ="SharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLayout);
        psw = findViewById(R.id.pswLayout);
        rememberCb = findViewById(R.id.rememberCb);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPswTxt = findViewById(R.id.forgotPswText);
        createAccountTxt = findViewById(R.id.registerText);

        loadData();

        createAccountTxt.setOnClickListener(view -> {
            Intent intentRegister = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intentRegister);
            finish();
        });

        forgotPswTxt.setOnClickListener(view -> {
            Intent intentForgotPsw = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(intentForgotPsw);
            finish();
        });

        loginBtn.setOnClickListener(view -> {
            String emailString = email.getEditText().getText().toString();
            String pswString = psw.getEditText().getText().toString();
            Boolean booleanEmail = false ,booleanPsw = false;


            booleanEmail = checkEmail(emailString);
            booleanPsw = checkPsw(pswString);

            if(booleanEmail) {
                if (booleanPsw) {
                    if (rememberCb.isChecked()) {
                        Toast.makeText(this, "You will be remembered", Toast.LENGTH_SHORT).show();
                        Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name","true");
                        editor.apply();
                        startActivity(goToHome);
                        finish();
                    }
                    else {
                        Toast.makeText(this, "You will not be remembered", Toast.LENGTH_SHORT).show();
                        Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(goToHome);
                        finish();
                    }
                } else
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();

        });

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String data = sharedPreferences.getString("name", "");
        if (data.equals("true")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

        private boolean checkPsw (String pswString){
            if (pswString.equals(""))
                return false;
            else
                return true;
        }

        private boolean checkEmail (String emailString){
            if (emailString.equals(""))
                return false;
            else
                return true;
        }

}