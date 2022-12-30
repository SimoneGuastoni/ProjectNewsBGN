package com.example.projectnewsbgn.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.homepage.MainActivity;

import java.io.IOException;

public class UserAccessActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String SHARED_PREFS_COUNTRY = "SharedPrefsCountry";
    public static final String COUNTRY = "country";

    public  ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_access);

        getSupportFragmentManager().beginTransaction().add(R.id.ua_fragment_container_view, LoginFragment.class,  null).commit();

    }

}
