package com.haard.mecasync;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class SplashScreen extends Activity {
	 
    
    private static int SPLASH_TIME_OUT = 7000;												// Splash screen timer
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        VideoView videoview = (VideoView) findViewById(R.id.videoView1);

        Uri video = Uri.parse("android.resource://com.haard.mecasync/raw/haard_logo");

        videoview.setVideoURI(video);
        videoview.start();
 
        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
        	Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();																		// close this activity
            }
        }, SPLASH_TIME_OUT);
    }
 
}
