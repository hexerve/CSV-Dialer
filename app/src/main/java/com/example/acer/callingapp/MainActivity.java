package com.example.acer.callingapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    Button mcall,mBrowse;
    private static final int PICK_FROM_GALLERY = 101;
    private static int RESULT_LOAD_IMAGE = 5;
    int nu = 0;
    int called;
    String todayDate;
    //    Button b;
    AlertDialog.Builder ab;
    //    String phoneNo;
    String num[] = new String[10];
    int i = 0, count = 0, j = 0,dial = 2;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Intent intent;

    public void alert() {
        Log.d(TAG, "alert: "+called);
        if(called<500) {
            ab = new AlertDialog.Builder(MainActivity.this);
            ab.setTitle("Call Option");
            ab.setMessage("Select one of the below");
            ab.setCancelable(false);
            ab.setPositiveButton("Redial", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent.setData(Uri.parse("tel:+91" + num[nu]));
                    startActivity(intent);
                    dial++;
                    count = 20;
                }
            });
            ab.setNegativeButton("Call Next No.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    nu++;
                    dial++;
                    intent.setData(Uri.parse("tel:+91" + num[nu]));
                    startActivity(intent);
                    count = 20;
                }
            });
        }
//                ab.setIcon()
        ab.show();
    }

//    void thread() {
//
//    new Handler().
//
//    postDelayed(new Runnable() {
//        @Override
//        public void run () {
//            button1.performClick();
//        }
//    },5000);
//}
//    private String getCallDetails() {
//
//        StringBuffer sb = new StringBuffer();
//        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
//                null, null, null);
//        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
//        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
//        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
//        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
////        Log.d(TAG, "getCallDetails: "+duration);
////        Log.d(TAG, "getCallDetails: "+number);
//        sb.append("Call Details :");
//        while (managedCursor.moveToNext()) {
//            String phNumber = managedCursor.getString(number);
//            String callType = managedCursor.getString(type);
//            String callDate = managedCursor.getString(date);
//            Date callDayTime = new Date(Long.valueOf(callDate));
//
//            String callDuration = managedCursor.getString(duration);
//            Log.d(TAG, "getCallDetails: "+callDuration);
//            Log.d(TAG, "getCallDetails: "+phNumber);
//            String dir = null;
//            int dircode = Integer.parseInt(callType);
//            switch (dircode) {
//                case CallLog.Calls.OUTGOING_TYPE:
//                    dir = "OUTGOING";
//                    break;
//
//                case CallLog.Calls.INCOMING_TYPE:
//                    dir = "INCOMING";
//                    break;
//
//                case CallLog.Calls.MISSED_TYPE:
//                    dir = "MISSED";
//                    break;
//            }
//            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
//                    + dir + " \nCall Date:--- " + callDayTime
//                    + " \nCall duration in sec :--- " + callDuration);
//            sb.append("\n----------------------------------");
//        }
//        managedCursor.close();
//        return sb.toString();
//
//    }

//    public void log(){
//        Uri allCalls = Uri.parse("content://call_log/calls");
//        Cursor c = managedQuery(allCalls, null, null, null, null);
//
//        String num= c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));// for  number
//        String name= c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
//        String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));// for duration
//        Log.d(TAG, "log: "+duration + "\nNUM:"+num);
//        int type = Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going.
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Delay();

        mBrowse = (Button) findViewById(R.id.browse);

//        log();
//        getCallDetails();
        readCallingData();


        mcall = (Button) findViewById(R.id.call);
        mcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                intent = new Intent(Intent.ACTION_CALL);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                intent.setData(Uri.parse("tel:+91" + num[0]));
//                Log.d(TAG, "onClick: " + num[1]);
//                startActivity(intent);
                count = 20;
                startActivity(intent);
//                alert();
                Log.i("helloo","asgsa");
//                count = 10;
//                i++;
                Log.d("MyActivity", "asda");
            }
        });
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: "+count);
        if (count==20){
            count=10;
        alert();
        }
        super.onResume();
    }

    private List<CallSample> callSamples = new ArrayList<>();

    private void readCallingData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
//            Step over header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line is " + line);
//                split by ','
                String[] tokens = line.split(",");
//                read the data
                num[j] = tokens[1];
                Log.d("MyActivity", "Numbers:" + num[j]);
                Log.d(TAG, "readCallingData: "+num[0]);
                j++;
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading datafile" + line, e);
            e.printStackTrace();
        }
    }
    public void Delay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                //Do something after 100ms
            }
        }, 5000);
    }
}