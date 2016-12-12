package com.esprit.android.inart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esprit.android.inart.adapters.DrawerSocialAdapter;
import com.esprit.android.inart.font.RobotoTextView;
import com.esprit.android.inart.provider.FragmentTags;
import com.esprit.android.inart.ui.AboutActivity;
import com.esprit.android.inart.ui.BaseActivity;
import com.esprit.android.inart.ui.fragments.CustomizeFragment;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Timer;

import butterknife.ButterKnife;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class addSpace extends BaseActivity {
    Button event,space,element;
    Button btn,postbtn;
    Bitmap bb;
    byte[] image;
    private Timer timer;
    public static String PREFERENCE_FILENAME = "reporting_app";
    private boolean isOpenActivitiesActivated = true;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    View fab,fabprog;
    private FABProgressCircle fabProgressCircle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    EditText songname;
    ImageView myimage;
    RobotoTextView esm;
    Uri selectedimg;
    EditText nameedit,descedit,adressedit;
    Spinner sp;
    public  String genre,song;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_space);

        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
        sp = (Spinner) findViewById(R.id.spinner2);
        nameedit = (EditText) findViewById(R.id.input_name);
        adressedit = (EditText) findViewById(R.id.input_adress);
        descedit = (EditText) findViewById(R.id.input_desc);
        btn = (Button) findViewById(R.id.phototagbtn);
        postbtn = (Button) findViewById(R.id.poostit);


        final String name = reportingPref.getString("name", "");
        String mail = reportingPref.getString("mail", "");
        String imgurl = reportingPref.getString("imgurl", "");

        ButterKnife.bind(this);
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

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Eventname = nameedit.getText().toString();
                String Description = descedit.getText().toString();
                String Adress = adressedit.getText().toString();
                String genre = sp.getSelectedItem().toString();
                ParseFile fileimage = new ParseFile("SpaceImage", image);
                // ParseFile parseAudioFile = new ParseFile("Music", data);
                //  parseAudioFile.saveInBackground();



                ParseObject parseObject = new ParseObject("Space");
                //  parseObject.put("AudioFile", parseAudioFile);
                parseObject.put("SpaceName", Eventname);
                parseObject.put("SpaceImage", fileimage);
                parseObject.put("Address",Adress);
               // parseObject.put("EventDate",date);
                parseObject.put("Owner", name);
                parseObject.put("Genre", genre);
                parseObject.put("Description", Description);
                parseObject.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Space file saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage().toString());
                        }


                    }
                });
            }});

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
        Glide.with(addSpace.this)
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
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(addSpace.this,
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

