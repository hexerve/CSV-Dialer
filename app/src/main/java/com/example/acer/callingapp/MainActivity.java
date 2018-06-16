package com.example.acer.callingapp;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText mphoneNo;
    Button mcall;
    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readCallingData();

        mphoneNo = (EditText) findViewById(R.id.phoneNo);
        mcall = (Button) findViewById(R.id.call);

        mcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNo = mphoneNo.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                intent.setData(Uri.parse("tel:+91" + phoneNo));
                startActivity(intent);
            }
        });
    }
    private List<CallSample> callSamples = new ArrayList<>();

    private void readCallingData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line="";
        try {
            //Step over header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity","Line is "+line);
                //split by ','
                String[] tokens = line.split(",");
                //read the data
                CallSample sample = new CallSample();
                if (tokens.length>=2&&tokens[1].length()>0) {
                    sample.setName(tokens[0]);
                }
                else sample.setName("");

                if (tokens.length>=2&&tokens[1].length()>0) {
                    sample.setPhone(tokens[1]);
                }
                else sample.setPhone("");
                callSamples.add(sample);
                    Log.d("MyActivity", "Just Created: " + sample);
                    Log.d("MyActivity", "Just Created2: " + tokens[0]);
                    Log.d("MyActivity", "Just Created2: " + tokens[1]);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity","Error reading datafile"+line,e);
            e.printStackTrace();
        }
    }
}