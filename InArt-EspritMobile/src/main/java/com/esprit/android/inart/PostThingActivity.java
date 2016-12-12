package com.esprit.android.inart;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.esprit.android.inart.Utils.IntentUtil;
import com.esprit.android.inart.Utils.PrefUtil;
import com.esprit.android.inart.Utils.Utils;
import com.esprit.android.inart.ui.BaseActivity;
import com.esprit.android.inart.ui.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class PostThingActivity extends BaseActivity {
    Button event,space,element;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_something);
        event = (Button) findViewById(R.id.posteevent);
        space = (Button) findViewById(R.id.postspace);
        element = (Button) findViewById(R.id.postelement);


        element.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String cat =  getIntent().getExtras().getString("categorie");
                if(cat.equals("photo")) {
                    Intent intent = new Intent(PostThingActivity.this, AddPhoto.class);

                    startActivity(intent);

                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                }
                if(cat.equals("music")) {
                    Intent intent = new Intent(PostThingActivity.this, AddMusic.class);

                    startActivity(intent);

                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                }

                if(cat.equals("video")) {
                    Intent intent = new Intent(PostThingActivity.this, addVideo.class);

                    startActivity(intent);

                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                }

                return false;
            }
        });
        event.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                    Intent intent = new Intent(PostThingActivity.this, PostEventActivity.class);

                    startActivity(intent);

                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);


                return false;
            }
        });

        space.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                Intent intent = new Intent(PostThingActivity.this, addSpace.class);

                startActivity(intent);

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);


                return false;
            }
        });

    }



}

