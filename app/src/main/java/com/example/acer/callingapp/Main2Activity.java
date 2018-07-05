package com.example.acer.callingapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends FragmentActivity {
    ConstraintLayout mlayout;
    TextView mdescrip;
    ImageView mimageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mlayout = findViewById(R.id.layout);
        mdescrip = findViewById(R.id.descrip);
        mimageView = findViewById(R.id.imageView);
        mdescrip.setOnTouchListener(new OnSwipeTouchListener(Main2Activity.this) {
            public void onSwipeRight() {
                onBackPressed();
            }
        });

        mdescrip.setOnTouchListener(new OnSwipeTouchListener(Main2Activity.this) {
                                       public void onSwipeRight() {
                                           onBackPressed();
                                       }
                                   });

        mlayout.setOnTouchListener(new OnSwipeTouchListener(Main2Activity.this) {
            public void onSwipeRight() {
                onBackPressed();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Main2Activity.this, MainActivity.class);
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation2,R.anim.animation4).toBundle();
        startActivity(homeIntent, bndlanimation);

    }
}
