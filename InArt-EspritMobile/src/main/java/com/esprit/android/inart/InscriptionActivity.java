package com.esprit.android.inart;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.esprit.android.inart.Utils.IntentUtil;
import com.esprit.android.inart.Utils.PrefUtil;
import com.esprit.android.inart.Utils.Utils;
import com.esprit.android.inart.ui.BaseActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class InscriptionActivity extends BaseActivity {
    LoginButton button_facebook;
    public static String PREFERENCE_FILENAME = "reporting_app";
    Button bbt,bbt2;
    String profileImgUrl;
    private CallbackManager callbackManager;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;
    String Name, Mail;
    LoginButton imgf;
    @Bind(R.id.image)
    ImageView mImageView;
    private boolean mIntentInProgress;

    private ConnectionResult mConnectionResult;

    String personName,personPhotoUrl,pemail;

    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private boolean mSignInClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inscription);
        ButterKnife.bind(this);
        mBackground = mImageView;
        moveBackground();
//        signOutFromGplus();



    }

}










