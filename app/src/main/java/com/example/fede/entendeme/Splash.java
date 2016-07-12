package com.example.fede.entendeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by fede on 11/6/2016.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final ImageView imgLogoUTN = (ImageView) findViewById(R.id.imgLogoUTN);
        final Animation animationRotate = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        imgLogoUTN.startAnimation(animationRotate);
        animationRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgLogoUTN.startAnimation(animationFadeOut);
                finish();
                Intent i = new Intent(getBaseContext(), Login.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
