package com.example.projectnewsbgn.UI.login;

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
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projectnewsbgn.Models.Account;
import com.example.projectnewsbgn.R;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class UserAccessActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "SharedPrefs";

    public static final String SHARED_PREFS_ACCOUNT = "SharedPrefsAccount";
    public static final String ACCOUNT= "Account";

    private NavController navController;

    public  ActivityResultLauncher<String> mGetContent = registerForActivityResult
            (new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ((ImageView) findViewById(R.id.profileIcon)).setImageBitmap(bitmap);
                        /* Creo un elemento bitmap in cui salvo l'immagine,
                        con la seconda riga trovo un imageView e gli carico l'immagine selezionata */
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_access);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.ua_fragment_container_view);
        navController = navHostFragment.getNavController();
    }

    @Override
    public void onBackPressed() {
        int currentDestination = navController.getCurrentDestination().getId();
        if(currentDestination == R.id.loginFragment){
        }
        else {
            super.onBackPressed();
        }
    }
}
