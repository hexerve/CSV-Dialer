package com.example.acer.callingapp;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Settings extends FragmentActivity {

    LinearLayout linearLayout;
    Button mlogout, mContact;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContact = findViewById(R.id.contact);
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+18002740561"));
                if (ActivityCompat.checkSelfPermission(Settings.this, Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            }
        });

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
                Settings.this.finish();
            }
        });

        linearLayout = findViewById(R.id.swipesettings);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(Settings.this) {
            public void onSwipeLeft() {
                onBackPressed();
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
