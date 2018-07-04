package com.example.acer.callingapp;

import android.Manifest;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;

import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.*;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
public class MainActivity extends AppCompatActivity {

    RadioButton r1,r2,r3;
    ConstraintLayout constraintLayout;


    private String TAG = MainActivity.class.getSimpleName();
    Button mcall,mBrowse,mdsample,mchat,msettings;
    private static int RESULT_LOAD_IMAGE = 5;
    static int nu = 0,i = 0, count = 0, j = 0,dial = 2;
    int called;
    AlertDialog ab;
    String num[] = new String[500];
    static int a = 0;
    int secondsLeft=-2;
    CountDownTimer countDownTimer;
    Intent intent;
    int check= 0;
    int timer = 12000;
    CheckBox checkBox;


    public void alert() {
        Log.d(TAG, "alert: " + called);
        if (called < 500) {
            ab = new AlertDialog.Builder(this).create();


            ab.setTitle("Call Option");
            ab.setMessage("");
            ab.setCancelable(false);
            ab.setButton(AlertDialog.BUTTON_POSITIVE, "Call Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    nu++;
                    dial++;
                    intent.setData(Uri.parse("tel:+91" + num[nu]));
                    startActivity(intent);
                    count = 20;
                    a = 1;
                    ab.cancel();
                    if (check ==0)
                        countDownTimer.cancel();
                }
            });
            ab.setButton(AlertDialog.BUTTON_NEGATIVE, "Redial", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent.setData(Uri.parse("tel:+91" + num[nu]));
                    startActivity(intent);
                    dial++;
                    count = 20;
                    a=1;
                    ab.cancel();
                    if (check ==0)
                        countDownTimer.cancel();
                }
            });
            ab.setButton(AlertDialog.BUTTON_NEUTRAL, "cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    a = 25;
                    ab.cancel();
                    if (check ==0)
                    countDownTimer.cancel();
                }
            });
            ab.show();
            Button b = ab.getButton(DialogInterface.BUTTON_POSITIVE);
            b.setTextColor(getResources().getColor(R.color.color2));
//            b.setBackgroundColor(getResources().getColor(R.color.colorAccen));
            b.setGravity(Gravity.CENTER);
            b = ab.getButton(DialogInterface.BUTTON_NEGATIVE);
            b.setTextColor(getResources().getColor(R.color.color2));
//            b.setBackgroundColor(getResources().getColor(R.color.colorAccen));
            b = ab.getButton(DialogInterface.BUTTON_NEUTRAL);
            b.setTextColor(getResources().getColor(R.color.color2));
//            b.setBackgroundColor(getResources().getColor(R.color.colorAccen));
        }
    }

    private String getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        Log.d(TAG, "getCallDetails: "+duration);
        Log.d(TAG, "getCallDetails: "+number);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));

            String callDuration = managedCursor.getString(duration);
            Log.d(TAG, "getCallDetails: "+callDuration+"  :"+callDate + " ");

            Log.d(TAG, "getCallDetails: "+phNumber);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
//
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
        }
        managedCursor.close();
        return sb.toString();

    }

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

    public void delay(){
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {

            }
        },5000);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.swipe);

        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                msettings.performClick();
            }

        });

//        getCallDetails();
        ZopimChat.init("5pCLnIhJdTjwZBfxZDgrSu0vrqG06iyy");
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        mchat = (Button) findViewById(R.id.chat);
        msettings = (Button) findViewById(R.id.settings);
        msettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(MainActivity.this, Settings.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
                startActivity(homeIntent, bndlanimation);

            }
        });
        mchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZopimChatActivity.class));


            }
        });
        try {
            PackageInfo info = getPackageManager().getPackageInfo("PROJECTNAME", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign= Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
                //textInstructionsOrLink = (TextView)findViewById(R.id.textstring);
                //textInstructionsOrLink.setText(sign);
                Toast.makeText(getApplicationContext(),sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("nope","nope");
        } catch (NoSuchAlgorithmException e) {
        }

        mBrowse = (Button) findViewById(R.id.browse);
        r1 = (RadioButton) findViewById(R.id.radio1);
        r2 = (RadioButton) findViewById(R.id.radio2);
        r3 = (RadioButton) findViewById(R.id.radio3);

        r1.setEnabled(false);
        r2.setEnabled(false);
        r3.setEnabled(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!checkBox.isChecked()){
                    r1.setEnabled(false);
                    r2.setEnabled(false);
                    r3.setEnabled(false);
                    check = 5;
                }
                else{
                    check = 0;
                    r1.setEnabled(true);
                    r2.setEnabled(true);
                    r3.setEnabled(true);

                }
            }
        });

        mBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("text/csv");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
                Log.i("helloo","asgsa");
            }
        });

        mcall = (Button) findViewById(R.id.call);
        mcall.setEnabled(false);
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
                count = 20;
                startActivity(intent);
                Log.i("helloo","asgsa");
//                count = 10;
//                i++;
                Log.d("MyActivity", "asda");

                if(r1.isChecked()){
                    timer = 5000;
                }
                else if(r2.isChecked()) {
                    timer = 10000;
                }
                else if(r3.isChecked()) {
                    timer = 20000;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            String text = uri.toString();
            try {
                Log.d("attachment: ", "asd" + text);
                InputStream in = getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in, Charset.forName("UTF-8"))
                );
                String line = "";
                Log.d("attachment4: ", "asd" + text);

//            Step over header
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    Log.d("MyActivity", "Line is " + line);
//                split by ','
                    String[] tokens = line.split(",");
//                read the data
                    num[j] = tokens[1];
                    Log.d("MyActivity", "Numbers:" + num[j]);
                    j++;


//                    FileInputStream is = new FileInputStream(f); // Fails on this line
//                    int size = is.available();
//                    byte[] buffer = new byte[size];
//                    is.read(buffer);
//                    is.close();
//                    text = new String(buffer);
//                    Log.d("attachment: ", text);
                }
                mcall.setEnabled(true);
                mdsample = (Button) findViewById(R.id.dsample);
//                mdsample.setBackgroundResource(R.drawable.callbutton);

                mcall.setBackgroundResource(R.drawable.button);
                mBrowse.setBackgroundResource(R.drawable.callbutton);
                Toast.makeText(this, "Your CSV has been uploaded", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onResume() {
        if (a!=25)
            a=0;
        Log.d(TAG, "onResume: "+count);
        if (count==20 && check == 0){
            count=10;
            alert();
            startTimer();
        }
        else if (count ==20&&check == 5)
            alert();
        super.onResume();
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timer, 1000) {

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    ab.setMessage("Select one of the below \n Next number will be redialled in " + secondsLeft + " sec");
                }
            }

            public void onFinish() {
                Log.d("asd", "sad");
                if (a == 0) {
                    Log.d(TAG, "run: delay");
                    nu++;
                    dial++;
                    intent.setData(Uri.parse("tel:+91" + num[nu]));
                    startActivity(intent);
                    count = 20;
                    a = 0;
                    ab.cancel();
                }
            }
        }.start();
    }
}