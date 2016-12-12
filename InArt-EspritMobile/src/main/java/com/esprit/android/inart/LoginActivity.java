package com.esprit.android.inart;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.esprit.android.inart.Utils.PrefUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.esprit.android.inart.Utils.IntentUtil;


public class LoginActivity extends AppCompatActivity {
    Button button_valider;
    Button button_twitter;
    LoginButton button_facebook;
    LinearLayout lay1;
    RelativeLayout lay2;
    TextView ed;
    TextView ed2;
    EditText mail;
    EditText pass;
    String Name;
    TextView Sign;
    private CallbackManager callbackManager;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        button_valider = (Button) findViewById(R.id.btn_login);
        button_twitter = (Button) findViewById(R.id.twitterbtn);
        button_facebook = (LoginButton) findViewById(R.id.facebookbtn);
        mail = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);
        ed = (TextView) findViewById(R.id.LOG);
        ed2 = (TextView) findViewById(R.id.PASS);
        Sign = (TextView) findViewById(R.id.link_signup);


       // Drawable draw1 = (Drawable)layer.findDrawableByLayerId(android.R.id.progress);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/heydings_icons.ttf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");

        lay1= (LinearLayout) findViewById(R.id.lin1);
        lay2= (RelativeLayout) findViewById(R.id.social);
        ed.setTypeface(type);
        ed2.setTypeface(type);
        mail.setTypeface(type2);
        pass.setTypeface(type2);



        prefUtil = new PrefUtil(this);
        intentUtil = new IntentUtil(this);


        button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
               // info.setText(message(profile));


                String userId = loginResult.getAccessToken().getUserId();
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken to SharedPreference
                prefUtil.saveAccessToken(accessToken);

                String profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
                Name = profile.getLastName();
                System.out.println(Name);
                Toast.makeText(LoginActivity.this, "Welcome " + Name, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "noooon", Toast.LENGTH_LONG).show();


            }
        });

     

  //

    }
    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("Welcome ").append(profile.getFirstName());
            System.out.println(profile.getName());
        }
        return stringBuffer.toString();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
