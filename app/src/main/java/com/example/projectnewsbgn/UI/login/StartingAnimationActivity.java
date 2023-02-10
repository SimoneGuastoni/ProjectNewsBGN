package com.example.projectnewsbgn.UI.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.UI.homepage.MainActivity;

public class StartingAnimationActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "SharedPrefs";

    private static final int Splash_animation = 2000;

    private boolean logged;

    private Animation topAnim,bottomAnim;
    private ImageView icon;
    private TextView welcome,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_starting_animation);

            icon = findViewById(R.id.icon);
            welcome = findViewById(R.id.welcome);
            slogan = findViewById(R.id.slogan);

            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//Set animation to elements
            icon.setAnimation(topAnim);
            welcome.setAnimation(bottomAnim);
            slogan.setAnimation(bottomAnim);

            loadData();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                        Intent intent;

                        if(logged){
                             intent = new Intent(StartingAnimationActivity.this,
                                     MainActivity.class);
                        }
                        else{
                             intent = new Intent(StartingAnimationActivity.this,
                                     UserAccessActivity.class);
                        }

                        Pair[] pairs = new Pair[2];
                        pairs[0] = new Pair<View,String>(icon,"logo_image");
                        pairs[1] = new Pair<View,String>(welcome,"logo_txt");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                                (StartingAnimationActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
            },Splash_animation);

        }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String data = sharedPreferences.getString("name", "");
        if (data.equals("true")) {
            logged = true;
        }
    }

}
