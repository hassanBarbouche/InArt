package com.esprit.android.inart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.Bind;
import android.annotation.TargetApi;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esprit.android.inart.Utils.Utils;
import com.esprit.android.inart.ui.BaseActivity;
import com.esprit.android.inart.ui.MainActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class FacebookLog extends BaseActivity {
    public static String PREFERENCE_FILENAME = "reporting_app";
    String name, mail, imgurl;
    TextView nom;
    TextView email;
    EditText ed;
    ImageView photo;
    Button enter, enter2, parsebtn;
    String nbrPhoto,nbrMusic,nbrVideo,nbrEvent;

    @Bind(R.id.image2)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebooklog);
        ButterKnife.bind(this);
        mBackground = mImageView;
        moveBackground();
        nom = (TextView) findViewById(R.id.nom);
        ed = (EditText) findViewById(R.id.input_password);
        photo = (ImageView) findViewById(R.id.photoface);
        enter = (Button) findViewById(R.id.enterbtn);
        enter2 = (Button) findViewById(R.id.enter2);
      //  parsebtn = (Button) findViewById(R.id.parsebtn);


        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

        name = reportingPref.getString("name", "");
        mail = reportingPref.getString("mail", "");
        imgurl = reportingPref.getString("imgurl", "");
        nom.setText(name);
        // email.setText(mail);
        Glide.with(FacebookLog.this)
                .load(imgurl)
                .into(photo);




        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
        query.whereEqualTo("Owner", name);
        //query.whereEqualTo("Genre", "Landscapes");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> conThus, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    nbrPhoto = conThus.size() + "";
                    //  System.out.println(nbrPhoto + "AAAA");


                }
            }
        });

        final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Music");
        query2.whereEqualTo("Owner", name);
        //query.whereEqualTo("Genre", "Landscapes");
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> conThus, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    nbrMusic = conThus.size() + "";
                  //  System.out.println(nbrPhoto + "AAAA");


                }
            }
        });

        final ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Event");
        query3.whereEqualTo("Owner", name);
        //query.whereEqualTo("Genre", "Landscapes");
        query3.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> conThus, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    nbrEvent = conThus.size() + "";
                    //  System.out.println(nbrPhoto + "AAAA");


                }
            }
        });

        final ParseQuery<ParseObject> query4 = ParseQuery.getQuery("Video");
        query4.whereEqualTo("Owner", name);
        //query.whereEqualTo("Genre", "Landscapes");
        query4.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> conThus, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    nbrVideo = conThus.size() + "";
                    //  System.out.println(nbrPhoto + "AAAA");


                }
            }
        });
        enter2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Save new user data into Parse.com Data Storage


                ParseUser user = new ParseUser();
                user.setUsername(name);
                user.setPassword(mail);
                user.put("ImageUrl", imgurl);




                user.logOut();


                //prefEditor.putString("id", user.getObjectId());


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            // Show a simple Toast message upon successful registration
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Signed up, Welcome",
                                    Toast.LENGTH_LONG).show();

                            SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                            SharedPreferences.Editor prefEditor = reportingPref.edit();
                            prefEditor.putString("nbrphoto", nbrPhoto);
                            prefEditor.putString("nbrmusic",nbrMusic);
                            prefEditor.putString("nbrevent", nbrEvent);
                            prefEditor.commit();
                            Intent intent = new Intent(FacebookLog.this, PageV.class);
                            intent.putExtra("nbrphoto",nbrPhoto);
                            intent.putExtra("nbrmusic",nbrMusic);
                            intent.putExtra("nbrevent",nbrEvent);
                            intent.putExtra("nbrvideo",nbrVideo);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getBaseContext(),
                                    "You are already a member !", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });







                return false;
            }
        });

        enter.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {



               /* SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("nbrphoto", nbrPhoto);
                prefEditor.putString("nbrmusic",nbrMusic);
                prefEditor.putString("nbrevent", nbrEvent);
                prefEditor.commit();

                Intent intent = new Intent(
                        FacebookLog.this,
                        PageV.class);
                startActivity(intent);

                return false;*/


                SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("nbrphoto", nbrPhoto);
                prefEditor.putString("nbrmusic",nbrMusic);
                prefEditor.putString("nbrevent", nbrEvent);
                prefEditor.putString("nbrvideo", nbrVideo);
                prefEditor.commit();

                Intent intent = new Intent(
                        FacebookLog.this,
                        MainActivity.class);
                startActivity(intent);

                return false;

            }
        });


    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Utils.hasHoneycomb()) {
            View demoContainerView = findViewById(R.id.image2);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap bb = BitmapFactory.decodeFile(filePath);
                    photo.setImageBitmap(bb);
                }
        }

    }
}
