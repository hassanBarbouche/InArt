package com.esprit.android.inart;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.esprit.android.inart.Utils.PrefUtil;
import com.esprit.android.inart.Utils.Utils;
import com.facebook.FacebookException;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.esprit.android.inart.Utils.IntentUtil;
import com.esprit.android.inart.ui.BaseActivity;
import com.esprit.android.inart.ui.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.io.InputStream;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class Welcome extends BaseActivity implements   GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ResultCallback<People.LoadPeopleResult>{
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
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mBackground = mImageView;
        moveBackground();
//        signOutFromGplus();
        bbt=(Button)findViewById(R.id.buttoninscri);
        btnSignIn = (SignInButton) findViewById(R.id.bbt);
        btnSignIn.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();



        prefUtil = new PrefUtil(this);
        intentUtil = new IntentUtil(this);
        imgf = (LoginButton) findViewById(R.id.imgf);
        imgf.setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
        //  bbt = (Button) findViewById(R.id.bbt);

        System.out.println("aaa");
        imgf.setReadPermissions(Arrays.asList("email"));


        imgf.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

try{
                Profile profile = Profile.getCurrentProfile();

                // info.setText(message(profile));


                String userId = loginResult.getAccessToken().getUserId();
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken to SharedPreference
                prefUtil.saveAccessToken(accessToken);
                // String mm = user.asMap().get("email");
                Name = profile.getLastName() + " " + profile.getFirstName();
                profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";}
catch(Exception e){

    System.out.println(e.getMessage().toString());
                                                     }

                SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("name", Name);
                prefEditor.putString("mail", Name + "@esprit.tn");
                prefEditor.putString("imgurl", profileImgUrl);
                prefEditor.commit();


                Intent intent = new Intent(Welcome.this, FacebookLog.class);
                startActivity(intent);
                //overridePendingTransition ( enterAnim,  exitAnim);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                System.out.println(Name);
                //  Toast.makeText(Welcome.this, "Welcome " + Mail, Toast.LENGTH_LONG).show();


            }



            @Override
            public void onCancel() {

            }


            @Override
            public void onError(FacebookException e) {
                Toast.makeText(Welcome.this, "noooon", Toast.LENGTH_LONG).show();


            }
        });

    /*    bbt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);


                return false;
            }
        });*/



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








    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Utils.hasHoneycomb()) {
            View demoContainerView = findViewById(R.id.image);
            demoContainerView.setAlpha(0);
            ViewPropertyAnimator animator = demoContainerView.animate();
            animator.alpha(1);
            if (Utils.hasICS()) {
                animator.setStartDelay(250);
            }
            animator.setDuration(1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    protected void onStart() {
        super.onStart();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }







    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
           // resolveSignInError();
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, GOOGLE_SIGIN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();

            }
        }
    }






    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                .setResultCallback(this);
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();



    }

    private void getProfileInformation() {
//        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback((ResultCallback<? super People.LoadPeopleResult>) this);
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();

                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + 400;

                String pemail = Plus.AccountApi.getAccountName(mGoogleApiClient);
                System.out.println(personName + pemail);
                SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("name", personName);
                prefEditor.putString("mail", pemail);
                prefEditor.putString("imgurl", personPhotoUrl);
                prefEditor.commit();


              //  Intent intent2 = new Intent(Welcome.this, FacebookLog.class);
                //startActivity(intent2);

               // signOutFromGplus();



                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X

                //   new LoadProfileImage(imageView_profile_image).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

        // Get user's information

    }


    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        //updateUI(false);
    }

    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.bbt:
                    // Signin button clicked
                 //   if (!mGoogleApiClient.isConnected())
                    signInWithGplus();
              /*     if (mGoogleApiClient.isConnected())
                    {  Intent intent2 = new Intent(Welcome.this, FacebookLog.class);
                    startActivity(intent2);}*/
                    break;

    }}



    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
           // updateUI(false);
        }
    }
    private static final int GOOGLE_SIGIN = 100;

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == GOOGLE_SIGIN) {
            if (responseCode == RESULT_OK) {
                mSignInClicked = false;
                // Update the UI after signin
                System.out.println("here");
                Intent intent2 = new Intent(Welcome.this, FacebookLog.class);
                startActivity(intent2);


            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }

        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, responseCode, intent);

        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
              //  resolveSignInError();
            }
        }

    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, GOOGLE_SIGIN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                   // Log.d(TAG, "Display name: " + personBuffer.get(i).getDisplayName());

                }
            } finally {
                personBuffer.release();
            }
        } else {
           // Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
        }
    }


}










