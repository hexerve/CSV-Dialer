package com.example.acer.callingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity {
    String emaill,passs;
    EditText pass,email;
    Button signIn;
    int count = 0;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn = (Button) findViewById(R.id.btn_signIn);
        pass = (EditText) findViewById(R.id.passIn);
        email = (EditText) findViewById(R.id.emailIn);
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SignIn.this,SignUp.class);
                startActivity(homeIntent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emaill = pass.getText().toString();
                passs = email.getText().toString();
                count=0;
                if (emaill.equals("")) {
                    count=10;
                    email.setError("Enter Valid Name");
                }
                if (passs.equals("")) {
                    count=10;
                    pass.setError("Enter Valid password");
                }
                if (count==0){
                    Intent homeIntent = new Intent(SignIn.this,MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        });


    }
}
