package com.example.projectnewsbgn.UI.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.projectnewsbgn.Models.Result;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.UI.login.AccountViewModel;
import com.example.projectnewsbgn.UI.login.UserAccessActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private AccountViewModel accountViewModel;
    private NewsViewModel newsViewModel;
    private MutableLiveData<Result> controlResult;
    public static final String SHARED_PREFS ="SharedPrefs";
    public static final String SHARED_PREFS_FETCH ="SharedPrefsFetch";
    public static final long TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.containerFragment);
        navController = navHostFragment.getNavController();

        bottomNavigationView = findViewById(R.id.bottomMenu);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,R.id.searchFragment,R.id.favouritesFragment,R.id.accountFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(50);
    }

   @Override
    public void onBackPressed() {
       int currentDestination = navController.getCurrentDestination().getId();
       if(currentDestination == R.id.homeFragment){
       }
       else {
           super.onBackPressed();
       }
    }

    private void changeRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name","false");
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        View view = findViewById(android.R.id.content);
        switch (item.getItemId()){
            case R.id.notification:
                Snackbar.make(view,"Notifications", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Snackbar.make(view, "Settings",Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.logout: {
                controlResult = accountViewModel.logOut();
                controlResult.observe(this, (Observer<Result>) result -> {
                    if (result.isSuccess()){
                        newsViewModel.clearAllDatabase().observe(this, result1 -> {
                            if (result1.isSuccess()){
                                changeRemember();
                                Intent intent = new Intent(MainActivity.this, UserAccessActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Snackbar.make(view,result1.getClass().toString(),Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Snackbar.make(view, result.getClass().toString(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}