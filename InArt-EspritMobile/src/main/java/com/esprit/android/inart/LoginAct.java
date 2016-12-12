package com.esprit.android.inart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esprit.android.inart.Utils.PrefUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.esprit.android.inart.R;
import com.esprit.android.inart.Utils.IntentUtil;


public class LoginAct extends AppCompatActivity {
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
        setContentView(R.layout.activity_log);




  //

    }



}
