package com.esprit.android.inart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.esprit.android.inart.ui.fragments.CustomizeFragment;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.esprit.android.inart.adapters.DrawerSocialAdapter;
import com.esprit.android.inart.font.RobotoTextView;
import com.esprit.android.inart.provider.FragmentTags;
import com.esprit.android.inart.ui.AboutActivity;
import com.esprit.android.inart.ui.BaseActivity;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.util.Timer;

import butterknife.ButterKnife;
//import com.daimajia.numberprogressbar.NumberProgressBar;


/**
 * Created by Hassan on 01/12/15.
 */
public class AddPhoto extends BaseActivity  {

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
    private FABProgressCircle fabProgressCircle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    EditText songname;
    ImageView myimage;
    RobotoTextView esm;
    Uri selectedimg;
    Spinner sp;
   public  String genre,song;

    //private NumberProgressBar bnp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);
        //btn = (Button) findViewById(R.id.addphotobtn);
        fab = (View) findViewById(R.id.fab);
        sp = (Spinner) findViewById(R.id.spinner3);
        songname = (EditText) findViewById(R.id.song_name);

       // String category = CategoriesListViewActivity.LIST_VIEW_OPTION_DRAG_AND_DROP;
      //  TextView spinnerText = (TextView) sp.getChildAt(0);

//        spinnerText.setTextColor(Color.WHITE);
        //sp.setPopupBackgroundResource(R.drawable.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.photography, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinners);
        sp.setAdapter(adapter);

        song= songname.getText().toString();

        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fabProgressCircle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fabProgressCircle.show();
                //  startYourAsynchronousJob();
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);



            }
        });
        ButterKnife.bind(this);
       // bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
     //   bnp.setOnProgressBarListener(this);
        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

        String name = reportingPref.getString("name", "");
        String mail = reportingPref.getString("mail", "");
        String imgurl = reportingPref.getString("imgurl", "");




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
        Glide.with(AddPhoto.this)
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
     //   new Get_User_Data().execute();
     //   new Get_User_Data().execute();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                     selectedimg = imageReturnedIntent.getData();


                    new Get_User_Data().execute();
                    //photo.setImageBitmap(bb);
                }
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
            Toast.makeText(AddPhoto.this,
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

    public class Get_User_Data extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(
                AddPhoto.this);

        protected void onPreExecute() {

         /*   this.dialog.setMessage("Loading...");
            this.dialog.setCancelable(false);
            this.dialog.show();*/
            fabProgressCircle.show();



        }

        @Override
        protected Void doInBackground(Void... params) {

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
       /*     timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp.incrementProgressBy(1);
                        }
                    });
                }
            }, 1000, 100);*/
            genre = sp.getSelectedItem().toString();
            song= songname.getText().toString();

            SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

           String nom = reportingPref.getString("name", "");
           // mail = reportingPref.getString("mail", "");
            ParseFile file = new ParseFile("Photo", image);
            //file.saveInBackground();

            ParseObject imgupload = new ParseObject("Photo");
            imgupload.put("ImageName", song);

            // Create a column named "ImageFile" and insert the image
            imgupload.put("ImageFile", file);
            imgupload.put("Owner", nom);
            imgupload.put("Genre", genre);

            // Create the class and the columns
           imgupload.saveInBackground();
            return null;

        }

        protected void onPostExecute(Void result) {
            fabProgressCircle.beginFinalAnimation();

            // Here if you wish to do future process for ex. move to another activity do here

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }
}
