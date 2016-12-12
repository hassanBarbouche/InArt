package com.esprit.android.inart;

/**
 * Created by Giovanni on 07/05/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.daimajia.numberprogressbar.NumberProgressBar;
import com.esprit.android.inart.ui.fragments.CustomizeFragment;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.esprit.android.inart.adapters.DrawerSocialAdapter;
import com.esprit.android.inart.font.RobotoTextView;
import com.esprit.android.inart.provider.FragmentTags;
import com.esprit.android.inart.ui.AboutActivity;
import com.esprit.android.inart.ui.BaseActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;

import butterknife.ButterKnife;


public class AddMusic extends BaseActivity {
    Button newfile, retrieve,  d_saverec, d_cancelrec;
    TextView d_countdown;
    String outputFile = null;
    MediaRecorder myRecorder;
    LinearLayout llrec, llsave;
    CountDownTimer countDownTimer;
    Context context = this;
    View Startrec,Stoprec;
    private FABProgressCircle fabProgressCircle;


    Button btn;
    Bitmap bb;
    byte[] image;
    private Timer timer;
    public static String PREFERENCE_FILENAME = "reporting_app";
    private boolean isOpenActivitiesActivated = true;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    View fab,fabprog;
   // private FABProgressCircle fabProgressCircle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ImageView myimage;
    EditText nameedit,descedit;
    RobotoTextView esm;
    Uri selectedimg;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_music);
        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fabProgressCirclerec);
        ButterKnife.bind(this);
        sp = (Spinner) findViewById(R.id.spinner2);
        nameedit = (EditText) findViewById(R.id.input_email);
        descedit = (EditText) findViewById(R.id.input_desc);
        btn = (Button) findViewById(R.id.phototagbtn);
       // final String genre = sp.getSelectedItem().toString();
       // final String Songname= nameedit.getText().toString();
        //final String Description = descedit.getText().toString();
      //  bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
       // bnp.setOnProgressBarListener(this);
        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

       final String name = reportingPref.getString("name", "");
        String mail = reportingPref.getString("mail", "");
        String imgurl = reportingPref.getString("imgurl", "");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fabProgressCircle.show();
                //  startYourAsynchronousJob();
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);



            }
        });

      //  newfile = (Button) findViewById(R.id.button);
       // retrieve = (Button) findViewById(R.id.button2);



              //  final Dialog dialog = new Dialog(context);
              //  dialog.setContentView(R.layout.activity_main_dialog);
              //  dialog.setTitle("New Audio File");

             //   llrec = (LinearLayout) dialog.findViewById(R.id.d_llrec);
              //  llsave = (LinearLayout) dialog.findViewById(R.id.d_llsave);
                Startrec = (View) findViewById(R.id.fabrec);
                Stoprec = (View) findViewById(R.id.fabstop);
                d_saverec = (Button) findViewById(R.id.btnsave);
                d_cancelrec = (Button) findViewById(R.id.btncancel);
                d_countdown = (TextView) findViewById(R.id.d_tvcountdown);

                //llrec.setVisibility(View.VISIBLE);
                Startrec.setVisibility(View.VISIBLE);
                Stoprec.setVisibility(View.GONE);
                d_cancelrec.setVisibility(View.GONE);
                d_saverec.setVisibility(View.GONE);
               // llsave.setVisibility(View.GONE);


                outputFile = Environment.getExternalStorageDirectory().
                        getAbsolutePath() + "/rumor.mp3";

                myRecorder = new MediaRecorder();
                myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myRecorder.setOutputFile(outputFile);

                countDownTimer = null;
                d_countdown.setText("30 s");

                Startrec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Startrec.setVisibility(View.GONE);
                        Stoprec.setVisibility(View.VISIBLE);
                        //llsave.setVisibility(View.GONE);
                        fabProgressCircle.show();


                        //  dialog.setCancelable(true);

                        countDownTimer = new CountDownTimer(30000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                d_countdown.setText(millisUntilFinished / 1000 + " s");
                            }

                            public void onFinish() {
                                d_countdown.setText("Time is up!");
                                Startrec.setVisibility(View.VISIBLE);
                                Stoprec.setVisibility(View.GONE);
                                d_saverec.setVisibility(View.VISIBLE);
                                d_cancelrec.setVisibility(View.VISIBLE);
                                fabProgressCircle.hide();

                                try {
                                    myRecorder.stop();
                                    myRecorder.release();
                                    myRecorder = null;

                                } catch (IllegalStateException e) {
                                    //  it is called before start()
                                    e.printStackTrace();
                                } catch (RuntimeException e) {
                                    // no valid audio/video data has been received
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        try {
                            myRecorder.prepare();
                            myRecorder.start();
                        } catch (IllegalStateException e) {
                            // start:it is called before prepare()
                            // prepare: it is called after start() or before setOutputFormat()
                            e.printStackTrace();
                        } catch (IOException e) {
                            // prepare() fails
                            e.printStackTrace();
                        }
                    }
                });

                Stoprec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDownTimer.cancel();
                       // dialog.setCancelable(false);
                        Startrec.setVisibility(View.VISIBLE);
                        Stoprec.setVisibility(View.GONE);
                        d_saverec.setVisibility(View.VISIBLE);
                        d_cancelrec.setVisibility(View.VISIBLE);
                        fabProgressCircle.hide();
                       // llsave.setVisibility(View.VISIBLE);
                        try {
                            myRecorder.stop();
                            myRecorder.release();
                            myRecorder = null;

                        } catch (IllegalStateException e) {
                            //  it is called before start()
                            e.printStackTrace();
                        } catch (RuntimeException e) {
                            // no valid audio/video data has been received
                            e.printStackTrace();
                        }
                    }
                });

                d_saverec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FileInputStream fileInputStream = null;
                        File fileObj = new File(outputFile);
                        byte[] data = new byte[(int) fileObj.length()];

                        try {
                            //convert file into array of bytes
                            fileInputStream = new FileInputStream(fileObj);
                            fileInputStream.read(data);
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                         String Songname= nameedit.getText().toString();
                         String Description = descedit.getText().toString();
                        String genre = sp.getSelectedItem().toString();
                        ParseFile fileimage = new ParseFile("Photo", image);
                        ParseFile parseAudioFile = new ParseFile("Music", data);
                        parseAudioFile.saveInBackground();

                        ParseObject parseObject = new ParseObject("Music");
                        parseObject.put("AudioFile", parseAudioFile);
                        parseObject.put("SongName",Songname );
                        parseObject.put("SongImage", fileimage);
                        parseObject.put("Owner", name);
                        parseObject.put("Genre", genre);
                        parseObject.put("Description", Description);
                        parseObject.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Audio file saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    System.out.println(e.getMessage().toString());
                                }
                            }
                        });

                       // dialog.dismiss();
                    }
                });

                d_cancelrec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  dialog.dismiss();
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
               // lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

              //  dialog.show();
               // dialog.getWindow().setAttributes(lp);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InArt");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_view);


        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        View headerView = getLayoutInflater().inflate(
                R.layout.header_navigation_drawer_social, mDrawerList, false);

        myimage = (ImageView) headerView.findViewById(R.id.header_navigation_drawer_social_image);
        esm = (RobotoTextView) headerView.findViewById(R.id.esm);
        esm.setText(name);
        Glide.with(AddMusic.this)
                .load(imgurl)
                .into(myimage);

        ImageView iv = (ImageView) headerView.findViewById(R.id.imageoo);
        mDrawerList.addHeaderView(headerView);// Add header before adapter (for
        // pre-KitKat)
        mDrawerList.setAdapter(new DrawerSocialAdapter(this));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        int color = getResources().getColor(R.color.material_grey_100);
        color = Color.argb(0xCD, Color.red(color), Color.green(color),
                Color.blue(color));
        mDrawerList.setBackgroundColor(color);
        mDrawerList.getLayoutParams().width = (int) getResources()
                .getDimension(R.dimen.drawer_width_social);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            //   mDrawerLayout.openDrawer(mDrawerList);
        }

            }


     /*   retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), AudioFilesList.class);
                startActivity(intent);
            }
        });*/



 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(AddMusic.this,
                    "You selected position: " + position, Toast.LENGTH_SHORT)
                    .show();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main, menu);
        MenuItem openActivities = menu.findItem(R.id.action_open_activities);
        openActivities.setChecked(isOpenActivitiesActivated);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_customize:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                break;
            case R.id.action_about:
                startActivityWith(AboutActivity.class);
                break;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityWith(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void manageFragment(Fragment newInstanceFragment, FragmentTags tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment currentIntanceFragment = findFragmentByTag(tag);
        if (currentIntanceFragment == null || (currentIntanceFragment != null && currentIntanceFragment.isHidden())) {
            if (currentIntanceFragment != null) {
                ft.show(currentIntanceFragment);
            } else {
                currentIntanceFragment = newInstanceFragment;
                ft.add(R.id.container, currentIntanceFragment, tag.toString());
                if (addToBackStack) {
                    ft.addToBackStack(null);
                }
            }
        } else {
            ft.hide(currentIntanceFragment);
            fm.popBackStack();
        }
        ft.commit();
    }

    private Fragment findFragmentByTag(FragmentTags tag) {
        return getSupportFragmentManager().findFragmentByTag(tag.toString());
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
                    selectedimg = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedimg, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    bb = BitmapFactory.decodeFile(filePath);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bb.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image = stream.toByteArray();


                   // new Get_User_Data().execute();
                    //photo.setImageBitmap(bb);
                }
        }

    }

}
