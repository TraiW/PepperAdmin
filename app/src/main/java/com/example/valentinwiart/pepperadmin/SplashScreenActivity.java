package com.example.valentinwiart.pepperadmin;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screeen);

        final TextView splash_text = (TextView) findViewById(R.id.splash_text);
        final ImageView splash_image = (ImageView) findViewById(R.id.splash_image);
        final Animation animation_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        animation_1.reset();
        animation_2.reset();

        splash_image.clearAnimation();
        splash_image.startAnimation(animation_1);

        splash_text.clearAnimation();
        splash_text.startAnimation(animation_2);


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                } catch (InterruptedException e) {

                } finally {
                    SplashScreenActivity.this.finish();
                }

            }
        };
        thread.start();
    }}