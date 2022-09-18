package com.example.an;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        /*ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();*/

       getSupportActionBar().hide();
        Timer t = new Timer();
        TimerTask splash = new TimerTask() {
            @Override
            public void run() {
                Intent app = new Intent(Splash.this,MainActivity.class);
                startActivity(app);
                finish();
            }
        };
       t.schedule(splash,5000);


    }
}