package com.example.an;

import static com.example.an.App.CHANNEL_1_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {
   private ViewPager2 viewPager2;
   private Handler slideHandler = new Handler();
   MediaPlayer mediaPlayer;
   ImageView fingerIcon;
   Button btnplay, btnpause,btnpopup,btnimagenes,btnmanos;
   NotificationManagerCompat notificationManager;
   Dialog myDialog;
   MaterialTapTargetPrompt.Builder builder;
    private long Timeback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.song4);
        fingerIcon = (ImageView) findViewById(R.id.fingerIcon);
        notificationManager = NotificationManagerCompat.from(this);
        myDialog = new Dialog(this);
        btnpopup = (Button) findViewById(R.id.button);
        btnimagenes = (Button) findViewById(R.id.button3);
        btnmanos = (Button) findViewById(R.id.button2);

        btnpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              myDialog.setContentView(R.layout.custompop);
              myDialog.show();
            }
        });

        btnimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.poemas);
                myDialog.show();
            }
        });

        btnmanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.manosjuntas);
                myDialog.show();
            }
        });


        fingerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying()){
                   mediaPlayer.start();
                   fingerIcon.setImageResource(R.drawable.ic_baseline_fingerprint_24);
                   //Toast.makeText(MainActivity.this,"Play Music",Toast.LENGTH_SHORT).show();
                   Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                           .setSmallIcon(R.mipmap.ic_launcher)
                           .setContentTitle(" ♡ Anniversary  ♡")
                           .setContentText("▶️Play Music")
                           .setPriority(NotificationCompat.PRIORITY_HIGH)
                           .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                           .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                           .build();
                   notificationManager.notify(1,notification);

                } else {
                  mediaPlayer.pause();
                  fingerIcon.setImageResource(R.drawable.ic_baseline_fingerprint_24);
                   // Toast.makeText(MainActivity.this,"Pause Music",Toast.LENGTH_SHORT).show();
                    Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_1_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("♡ Anniversary ♡")
                            .setContentText("⏸️Pause Music")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();
                    notificationManager.notify(1,notification);
                }
            }
        });


        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        List<SliderItem> sliderItems = new ArrayList<>();

        // Here, i'm preparing list of images from drawable
        // you can get it from API as well

        sliderItems.add(new SliderItem(R.drawable.ele));
        sliderItems.add(new SliderItem(R.drawable.i));
        sliderItems.add(new SliderItem(R.drawable.s));
        sliderItems.add(new SliderItem(R.drawable.a));
        sliderItems.add(new SliderItem(R.drawable.lmin));
        sliderItems.add(new SliderItem(R.drawable.e));
        sliderItems.add(new SliderItem(R.drawable.x));


        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(4));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {

            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable,3000);// Slide duration 3 Seconds

            }
        });

        getSupportActionBar().hide();

        //URL SOURCE: https://sjwall.github.io/MaterialTapTargetPrompt/?language=java
        builder = new MaterialTapTargetPrompt.Builder(MainActivity.this);
        builder.setTarget(R.id.imageView);
        builder.setPrimaryText("Touch The Diamond");
        builder.setSecondaryText("You can play or pause the song");
        builder.setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
        {
            @Override
            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
            {
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                {
                    // User has pressed the prompt target
                    builder = new MaterialTapTargetPrompt.Builder(MainActivity.this);
                    builder.setTarget(R.id.button);
                    builder.setPrimaryText("Touch The Hexagon");
                    builder.setSecondaryText("Open to see the  gift");
                    builder.setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                    {
                        @Override
                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                        {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                            {

                            }
                        }
                    });
                    builder.show();
                }
            }
        });
        builder.show();
    }
    public void playMusic(View view){
        mediaPlayer.start();
    }
    public void pauseMusic(View view){
        mediaPlayer.pause();
    }
    public void stopMusic(View view){
        mediaPlayer.stop();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (System.currentTimeMillis() - Timeback > 1000) {
            Timeback = System.currentTimeMillis();
            Toast.makeText(this, "♥♥♥ Press Again to Exit ♥♥♥", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaPlayer.pause();
        Toast.makeText(this, "Bye Bby!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }
    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable,3000);
    }

}