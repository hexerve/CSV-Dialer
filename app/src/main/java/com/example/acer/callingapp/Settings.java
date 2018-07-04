package com.example.acer.callingapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Settings extends FragmentActivity {

    Settings settings;
    LinearLayout linearLayout;
    Button mlogout;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mlogout = findViewById(R.id.logout);
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences("file", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putInt("int", 0);
                editor.commit();
                Intent homeIntent = new Intent(Settings.this, SignUp.class);
                startActivity(homeIntent);
                finish();
            }
        });

        linearLayout = findViewById(R.id.swipesettings);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(Settings.this) {
            public void onSwipeLeft() {
                Settings.this.onBackPressed();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Settings.this, MainActivity.class);
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation2,R.anim.animation).toBundle();
        startActivity(homeIntent, bndlanimation);
    }
}
