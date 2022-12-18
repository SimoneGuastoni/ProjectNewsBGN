package com.example.projectnewsbgn.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
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

public class StartingAnimationActivity extends AppCompatActivity {

    private static int Splash_animation = 2000;

    Animation topAnim,bottomAnim;
    ImageView icon;
    TextView welcome,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(StartingAnimationActivity.this,UserAccessActivity.class);

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(icon,"logo_image");
                    pairs[1] = new Pair<View,String>(welcome,"logo_txt");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartingAnimationActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            },Splash_animation);

        }

    }
