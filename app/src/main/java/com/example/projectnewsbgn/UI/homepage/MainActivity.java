package com.example.projectnewsbgn.UI.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.projectnewsbgn.R;
/*import com.example.projectnewsbgn.login.LoginActivity;*/
import com.example.projectnewsbgn.UI.login.UserAccessActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentWindow;
    public static final String SHARED_PREFS ="SharedPrefs";
    public static final String SHARED_PREFS_FETCH ="SharedPrefsFetch";
    public static final long TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* HomeFragment homeFragment = new HomeFragment();
        AccountFragment accountFragment = new AccountFragment();
        FavoritesFragment favouritesFragment = new FavoritesFragment();
        SearchFragment searchFragment = new SearchFragment();
*/
        /*Toolbar toolbar = findViewById(R.id.topBarMain);
        setSupportActionBar(toolbar);*/

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.containerFragment);
        navController = navHostFragment.getNavController();

        bottomNavigationView = findViewById(R.id.bottomMenu);
        //fragmentWindow = findViewById(R.id.containerFragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,R.id.searchFragment,R.id.favouritesFragment,R.id.accountFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        /*getSupportFragmentManager().beginTransaction().add(R.id.container,HomeFragment.class,null).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                        return true;
                    case R.id.favourites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,favouritesFragment).commit();
                        return true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                        return true;
                }
                return false;
            }
        });*/
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
        switch (item.getItemId()){
            case R.id.notification:
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                changeRemember();
                Intent intent = new Intent(MainActivity.this, UserAccessActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}