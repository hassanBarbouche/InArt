package com.esprit.android.inart.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esprit.android.inart.FacebookLog;
import com.esprit.android.inart.MyEventList;
import com.esprit.android.inart.MyMusicList;
import com.esprit.android.inart.MyPhotosList;
import com.esprit.android.inart.SpaceElement;
import com.esprit.android.inart.Welcome;
import com.esprit.android.inart.ui.fragments.CustomizeFragment;
import com.esprit.android.inart.R;
import com.esprit.android.inart.Utils.SharePreferences;
import com.esprit.android.inart.adapters.DrawerSocialAdapter;
import com.esprit.android.inart.font.RobotoTextView;
import com.esprit.android.inart.provider.FragmentTags;
import com.esprit.android.inart.ui.fragments.ListBuddiesFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CustomizeFragment.OnCustomizeListener {

    public static String PREFERENCE_FILENAME = "reporting_app";
    private boolean isOpenActivitiesActivated = true;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    String nbrPhoto,nbrMusic,nbrEvent,nbrVideo;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ImageView myimage;
    RobotoTextView esm ,photonn,musicc,eventt,videoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle("InArt");

        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

       String name = reportingPref.getString("name", "");
       String mail = reportingPref.getString("mail", "");
        String imgurl = reportingPref.getString("imgurl", "");
         nbrPhoto =  reportingPref.getString("nbrphoto", "");
        nbrEvent =  reportingPref.getString("nbrevent", "");
        nbrMusic =  reportingPref.getString("nbrmusic", "");
        nbrVideo =  reportingPref.getString("nbrvideo", "");
//        mDrawerLayout.closeDrawer(mDrawerList);
       // nbrPhoto =  getIntent().getExtras().getString("nbrphoto");
//////////////////////////

       /* final ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
        query.whereEqualTo("Owner", name);
        //query.whereEqualTo("Genre", "Landscapes");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> conThus, ParseException e) {
                if (e == null) {
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                   nbrPhoto= conThus.size() + "";
                    System.out.println(nbrPhoto + "AAAA");




                }}});*/








        //////////////////////////






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InArt");
        getSupportActionBar().setIcon(R.drawable.logoart);

       // mDrawerLayout.closeDrawer(mDrawerList);
        //mDrawerLayout.closeDrawers();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_view);



        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        View headerView = getLayoutInflater().inflate(
                R.layout.header_navigation_drawer_social, mDrawerList, false);

        myimage = (ImageView) headerView.findViewById(R.id.header_navigation_drawer_social_image);
        esm = (RobotoTextView) headerView.findViewById(R.id.esm);
        photonn = (RobotoTextView) headerView.findViewById(R.id.nbrphoto);
        musicc = (RobotoTextView) headerView.findViewById(R.id.nbr_music);
        eventt = (RobotoTextView) headerView.findViewById(R.id.nbr_event);
        videoo = (RobotoTextView) headerView.findViewById(R.id.nbr_video);


        photonn.setText(nbrPhoto);
        eventt.setText(nbrEvent);
        musicc.setText(nbrMusic);
        videoo.setText(nbrVideo);

        esm.setText(name);
        Glide.with(MainActivity.this)
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

       // mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

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
          //  mDrawerLayout.openDrawer(mDrawerList);
        }

        /////////////////
        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }
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
            case R.id.action_open_activities:
                onOpenActivitiesClick(item);
                break;
            case R.id.action_reset:
            { SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = reportingPref.edit();
                editor.clear();
                editor.commit();
                Intent intent2 = new Intent(MainActivity.this, Welcome.class);

                startActivity(intent2);}
                break;
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
    public void setSpeed(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setSpeed(value);
        }
    }

    @Override
    public void setGap(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGap(value);
        }
    }

    @Override
    public void setGapColor(int color) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGapColor(color);
        }
    }

    @Override
    public void setDivider(Drawable drawable) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDivider(drawable);
        }
    }

    @Override
    public void setDividerHeight(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDividerHeight(value);
        }
    }

    @Override
    public void setAutoScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setAutoScrollFaster(option);
        }
    }

    @Override
    public void setScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setScrollFaster(option);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void resetLayout() {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.resetLayout();
            reset();
            CustomizeFragment customizeFragment = (CustomizeFragment) findFragmentByTag(FragmentTags.CUSTOMIZE);
            if (customizeFragment != null) {
                customizeFragment.reset();
            }
        }
    }

    private void reset() {
        SharePreferences.reset();
    }

    public boolean onOpenActivitiesClick(MenuItem menuItem) {
        isOpenActivitiesActivated = !menuItem.isChecked();
        menuItem.setChecked(isOpenActivitiesActivated);
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setOpenActivities(isOpenActivitiesActivated);
        }

        return false;
    }

    private ListBuddiesFragment getListBuddiesFragment() {
        return (ListBuddiesFragment) findFragmentByTag(FragmentTags.LIST_BUDDIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    ///////************////////////
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }



    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            if(position==2)
            {

                Intent intent = new Intent(
                        MainActivity.this,
                        MyPhotosList.class);
                startActivity(intent);


            }

            if(position==1)
            {

                Intent intent = new Intent(
                        MainActivity.this,
                        MyMusicList.class);
                startActivity(intent);


            }

            if(position==4)
            {

                Intent intent = new Intent(
                        MainActivity.this,
                        MyEventList.class);
                startActivity(intent);


            }
         /*   Toast.makeText(MainActivity.this,
                    "You selected position: " + position, Toast.LENGTH_SHORT)
                    .show();*/
//            mDrawerLayout.closeDrawer(mDrawerList);
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
}
