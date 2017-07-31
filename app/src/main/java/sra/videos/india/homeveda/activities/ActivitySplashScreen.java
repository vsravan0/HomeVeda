package sra.videos.india.homeveda.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import sra.videos.india.homeveda.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(ActivitySplashScreen.this,ActivityPlayLists.class));
            }
        },1000);


    }

    private Handler mHandler = new Handler();







}
