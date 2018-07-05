package com.example.acer.callingapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUp extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{


    private EditText mPhoneNumber;
    static int abcd = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    int count = 0;
    EditText email, pass, name;
    EditText phone;
    Button signUp;

    CallbackManager callbackManager;

    LoginButton loginButton;

    GoogleApiClient googleApiClient;
    int REQ_CODE = 9001;
    SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_up);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        setGooglePlusButtonText(signInButton,"Google Sign In");
        signInButton.setOnClickListener((View.OnClickListener) this);
        signInButton.setSize(SignInButton.COLOR_DARK);


        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        signUp = (Button) findViewById(R.id.btn_signup);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.input_email);
        name = (EditText) findViewById(R.id.input_name);
        mPhoneNumber = (EditText) findViewById(R.id.phone);


        loginButton = (LoginButton) findViewById(R.id.login_button);

            loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_gender", "user_friends"));

        AccessToken accessToken = AccessToken.getCurrentAccessToken();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String name = object.getString("name");
                                    final String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                    String gender = object.getString("gender"); // 01/31/1980 format
                                    Log.d("tag", "Name : "+name+"\nEmail : " + email + "\nBirthday : " + birthday + "\nGender : " + gender);
                                    final String data = "Name : "+name+"\nEmail : " + email + "\nBirthday : " + birthday + "\nGender : " + gender;
                                    int count1 = 10;

                                    editor.putInt("int", count1);
                                    editor.commit();

                                    Thread t = new Thread() {
                                        public void run() {

                                            String to = "hexerve@gmail.com";
                                            String from = "hexerve@gmail.com";
                                            String pass = "Software@123";

                                            String host = "smtp.gmail.com";
                                            try {
                                                Properties p = new Properties();

                                                Session sess = Session.getInstance(p);

                                                MimeMessage msgg = new MimeMessage(sess);

                                                InternetAddress toId = new InternetAddress(to);
                                                InternetAddress fromId = new InternetAddress(from);

                                                msgg.setRecipient(Message.RecipientType.TO, toId);
                                                msgg.setFrom(fromId);
                                                msgg.setSubject("Registration Sucessfull");

                                                msgg.setText(data);

                                                Transport tpt = sess.getTransport("smtps");
                                                tpt.connect(host, from, pass);
                                                tpt.sendMessage(msgg, msgg.getAllRecipients());
                                            } catch (Exception e) {
                                            }
                                        }
                                    };
                                    t.start();


                                    Intent homeIntent = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(homeIntent);
                                    finish();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                String s = parameters.getString("gender", "female");
                Log.d("GENDER", "GG" + s);
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }

        });


        preferences = getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = preferences.edit();


        abcd = preferences.getInt("int", 0);
        Log.d("asd", "count" + abcd);
        if (abcd == 10) {
            Intent homeIntent = new Intent(SignUp.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
        signUp.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          final String eemail, epassword, ephone, ename;
                                          ename = name.getText().toString();
                                          ephone = phone.getText().toString();

                                          epassword = pass.getText().toString();
                                          eemail = email.getText().toString();
                                          count = 0;
                                          if (ename.equals("")) {
                                              name.setError("Enter Valid Name");
                                              count = 10;
                                          }
                                          if (epassword.equals("")) {
                                              count = 10;
                                              pass.setError("Enter Valid password");
                                          }
                                          if (eemail.equals("")) {
                                              count = 10;
                                              email.setError("Enter Valid Email");
                                          }
                                          if (ephone.equals("")) {
                                              count = 10;
                                              phone.setError("Enter Valid Phone No.");
                                          }
                                          if (count == 0) {
//                                              onButtonClicked();
                                              String email = eemail;
                                              Thread t = new Thread() {
                                                  public void run() {

                                                      String name = ename;
                                                      String phone = ephone;
                                                      String to = "hexerve@gmail.com";
                                                      String from = "hexerve@gmail.com";
                                                      String pass = "Software@123";

                                                      String host = "smtp.gmail.com";
                                                      try {
                                                          Properties p = new Properties();

                                                          Session sess = Session.getInstance(p);

                                                          MimeMessage msgg = new MimeMessage(sess);

                                                          InternetAddress toId = new InternetAddress(to);
                                                          InternetAddress fromId = new InternetAddress(from);

                                                          msgg.setRecipient(Message.RecipientType.TO, toId);
                                                          msgg.setFrom(fromId);
                                                          msgg.setSubject("Registration Sucessfull");

                                                          msgg.setText("Dear " + name + " you are sucessfully registered .\nYour details are" + "\nName : " + name + "\nEmail : " + eemail + "\nMobile No. : " + ephone);

                                                          Transport tpt = sess.getTransport("smtps");
                                                          tpt.connect(host, from, pass);
                                                          tpt.sendMessage(msgg, msgg.getAllRecipients());
                                                      } catch (Exception e) {
                                                      }
                                                  }
                                              };
                                              t.start();
                                          }
                                          int count1 = 10;

                                          editor.putInt("int", count1);
                                          editor.commit();

                                          Intent homeIntent = new Intent(SignUp.this, MainActivity.class);
                                          startActivity(homeIntent);
                                          finish();
                                      }

                                  }


        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                handleResult(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    public void handleResult(GoogleSignInResult result ) throws IOException {

        GoogleSignInAccount account = result.getSignInAccount();

        if (result.isSuccess()) {
            String email = account.getEmail();
            String name = account.getDisplayName();
            Log.d("USER DATA", "NAME:" + name + "\nEMAIL:" + email);
            final String data ="NAME:" + name + "\nEMAIL:" + email;
            Thread t = new Thread() {
                public void run() {

                    String to = "hexerve@gmail.com";
                    String from = "hexerve@gmail.com";
                    String pass = "Software@123";

                    String host = "smtp.gmail.com";
                    try {
                        Properties p = new Properties();

                        Session sess = Session.getInstance(p);

                        MimeMessage msgg = new MimeMessage(sess);

                        InternetAddress toId = new InternetAddress(to);
                        InternetAddress fromId = new InternetAddress(from);

                        msgg.setRecipient(Message.RecipientType.TO, toId);
                        msgg.setFrom(fromId);
                        msgg.setSubject("Registration Sucessfull");

                        msgg.setText(data);

                        Transport tpt = sess.getTransport("smtps");
                        tpt.connect(host, from, pass);
                        tpt.sendMessage(msgg, msgg.getAllRecipients());
                    } catch (Exception e) {
                    }
                }
            };
            t.start();
        }
        int count1 = 10;

        editor.putInt("int", count1);
        editor.commit();

        Intent homeIntent = new Intent(SignUp.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }
}