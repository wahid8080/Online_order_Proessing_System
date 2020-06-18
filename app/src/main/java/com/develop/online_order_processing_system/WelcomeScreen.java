package com.develop.online_order_processing_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeScreen extends AppCompatActivity {

    Animation upDawn,downUp;
    ImageView flashScreen;
    Button button;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        flashScreen = findViewById(R.id.imageForFlashScreen);
        button = findViewById(R.id.gotologinid);
        upDawn = AnimationUtils.loadAnimation(this,R.anim.up_down_animation);
        downUp = AnimationUtils.loadAnimation(this,R.anim.down_up);
        user = FirebaseAuth.getInstance().getCurrentUser();

        flashScreen.setAnimation(upDawn);
        button.setAnimation(downUp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null)
                {
                    startActivity(new Intent(WelcomeScreen.this,Empty.class));
                    finish();
                } else
                {
                    startActivity(new Intent(WelcomeScreen.this,Login.class));
                    finish();
                }

            }
        });

    }
}
