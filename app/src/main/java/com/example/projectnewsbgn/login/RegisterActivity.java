package com.example.projectnewsbgn.login;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.object.Account;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout accountName,email,psw,confirmPsw;
    private Button registerBtn;
    private ImageView profilePic;
    private CheckBox rememberCb;
    public static final String SHARED_PREFS ="SharedPrefs";

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ((ImageView) findViewById(R.id.profileIcon)).setImageBitmap(bitmap);
                        /* Creo un elemento bitmap in cui salvo l'immagine, con la seconda riga trovo un imageView e gli carico l'immagine selezionata */
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profilePic = findViewById(R.id.profileIcon);
        registerBtn = findViewById(R.id.registerBtn);
        rememberCb = findViewById(R.id.rememberCbRegister);
        accountName = findViewById(R.id.account);
        email = findViewById(R.id.email);
        psw = findViewById(R.id.psw);
        confirmPsw = findViewById(R.id.confirmPsw);


        profilePic.setOnClickListener(view -> {
            mGetContent.launch("image/*");
        });

       registerBtn.setOnClickListener(view -> {
            String emailString,pswString,pswStringConf,accountString;

            accountString = accountName.getEditText().getText().toString();
            emailString = email.getEditText().getText().toString();
            pswString = psw.getEditText().getText().toString();
            pswStringConf = confirmPsw.getEditText().getText().toString();

            if(controlAccountString(accountString)){
                if (controlEmailString(emailString)){
                    if(controlPsw(pswString) && controlPsw(pswStringConf)){
                        if (comparePsw(pswString,pswStringConf)) {
                            if (rememberCb.isChecked()) {
                                Toast.makeText(this, "You will be remembered", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name","true");
                                editor.apply();
                                Intent goToSelectInterest = new Intent(RegisterActivity.this, SelectionInterestActivity.class);
                                Account account = new Account(accountString,emailString,pswString,"",false,false,false);
                                goToSelectInterest.putExtra("user",account);
                                /* goToSelectInterest.putExtra("name",accountString);
                                goToSelectInterest.putExtra("email",emailString);
                                goToSelectInterest.putExtra("psw",pswString);
                                goToSelectInterest.putExtra("icon",profilePic.get);*/
                                startActivity(goToSelectInterest);
                                finish();
                            } else {
                                Toast.makeText(this, "You will not be remembered", Toast.LENGTH_SHORT).show();
                                Intent goToSelectInterest = new Intent(RegisterActivity.this, SelectionInterestActivity.class);
                                Account account = new Account(accountString,emailString,pswString,"",false,false,false);
                                goToSelectInterest.putExtra("user",account);
                                startActivity(goToSelectInterest);
                                finish();
                            }
                        }
                        else
                            confirmPsw.setError("Passwords doesn't match");
                        }
                    else
                        psw.setError("Invalid password");
                    }
                else
                    email.setError("Invalid email");
                }
            else
                accountName.setError("Invalid account name");
        });

    }

    private boolean comparePsw(String pswString, String pswStringConf) {
        if (pswString.equals(pswStringConf))
            return true;
        else
            return false;
    }

    private boolean controlPsw(String pswStringConf) {
        if (pswStringConf.equals(""))
            return false;
        else
            return true;
    }

    private boolean controlEmailString(String emailString) {
        if(emailString.equals(""))
            return false;
        else
            return true;
    }

    private boolean controlAccountString(String accountString) {
        if(accountString.equals(""))
            return false;
        else
            return true;
    }
}