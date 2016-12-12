package com.esprit.android.inart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.esprit.android.inart.adapters.ImageGallerySubcategoryAdapter;
import com.esprit.android.inart.models.ImageGallerySubcategoryModel;
import com.esprit.android.inart.ui.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PhotoMine extends BaseActivity {

    public static final String IMAGE_GALLERY_SUBCATEGORY = "com.csform.android.uiapptemplate.ImageGallerySubcategoryActivity";
    public static final String IMAGE_GALLERY_LAYOUT_ON_TOP = "com.csform.android.uiapptemplate.ImageGallerySubcategoryActivity.layoutOnTop";
    private ArrayList<ImageGallerySubcategoryModel> mSubcategories;
    public static String PREFERENCE_FILENAME = "reporting_app";
    private ListView mListView;
    private boolean mIsLayoutOnTop;
    List<PhotoElement> mang;
    String name;
    protected ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_parse);
        mang = new ArrayList<>() ;
        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        name = reportingPref.getString("name", "");
        mListView = (ListView) findViewById(R.id.list_view);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(IMAGE_GALLERY_SUBCATEGORY)) {
                mSubcategories = extras.getParcelableArrayList(IMAGE_GALLERY_SUBCATEGORY);
            } else {
                mSubcategories = new ArrayList<>();
            }
            if (extras.containsKey(IMAGE_GALLERY_LAYOUT_ON_TOP)) {
                mIsLayoutOnTop = extras.getBoolean(IMAGE_GALLERY_LAYOUT_ON_TOP, false);
            }
        }

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
        query.whereEqualTo("Genre", "Portrait");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> conThus, ParseException e) {
                if (e == null) {
                    System.out.println("noproblem");
                    //Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for (int i = 0; i < conThus.size(); i++) {
                        ParseObject dong = conThus.get(i);
                        System.out.println(dong.getString("ImageName"));
                        //System.out.println(dong.getParseFile("ImageFile").toString());
                        mang.add(new PhotoElement(
                                dong.getString("ImageName"),
                                dong.getString("ImageName"),
                                dong.getParseFile("ImageFile"),
                                dong.getParseFile("ImageFile").getUrl(),
                                dong.getString("Owner"),
                                dong.getInt("Likes")


                        ));
                    }
                } else System.out.println(e.getMessage().toString());
            }
        });
        System.out.println(mang.size()+" this is size");
        BaseAdapter adapter = new ImageGallerySubcategoryAdapter(this, (ArrayList<PhotoElement>)mang, mIsLayoutOnTop);
        mListView.setAdapter(adapter);
        //setAdapter();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        BaseAdapter adapter = new ImageGallerySubcategoryAdapter(this, (ArrayList<PhotoElement>)mang, mIsLayoutOnTop);
        mListView.setAdapter(adapter);
    }
}
