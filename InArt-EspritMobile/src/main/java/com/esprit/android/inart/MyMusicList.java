package com.esprit.android.inart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.esprit.android.inart.adapters.ImageGallerySubcategoryAdapterMusic;
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

public class MyMusicList extends BaseActivity {

	public static final String IMAGE_GALLERY_SUBCATEGORY = "com.csform.android.uiapptemplate.ImageGallerySubcategoryActivity";
	public static final String IMAGE_GALLERY_LAYOUT_ON_TOP = "com.csform.android.uiapptemplate.ImageGallerySubcategoryActivity.layoutOnTop";
	private ArrayList<ImageGallerySubcategoryModel> mSubcategories;
    public static String PREFERENCE_FILENAME = "reporting_app";
	private ListView mListView;
	private boolean mIsLayoutOnTop;
	List<MusicElement> mang;
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

		final ParseQuery<ParseObject> query = ParseQuery.getQuery("Music");
		query.whereEqualTo("Owner", name);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> conThus, ParseException e) {
				if (e == null) {
					System.out.println("noproblem");
					//Log.d("score", "Retrieved " + scoreList.size() + " scores");
					for (int i = 0; i < conThus.size(); i++) {
						ParseObject dong = conThus.get(i);
						System.out.println(dong.getString("ImageName"));
						//System.out.println(dong.getParseFile("ImageFile").toString());
						mang.add(new MusicElement(
								dong.getString("SongName"),
								dong.getString("Description"),
								dong.getParseFile("AudioFile"),
								dong.getString("Genre"),
								dong.getString("Owner"),
								dong.getParseFile("SongImage").getUrl()


						));
					}
					BaseAdapter adapter = new ImageGallerySubcategoryAdapterMusic(getBaseContext(), (ArrayList<MusicElement>) mang, mIsLayoutOnTop);
					mListView.setAdapter(adapter);
				} else System.out.println(e.getMessage().toString());
			}
		});
        System.out.println(mang.size() + " this is size");

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
				BaseAdapter adapter = new ImageGallerySubcategoryAdapterMusic(this, (ArrayList<MusicElement>)mang, mIsLayoutOnTop);
				mListView.setAdapter(adapter);
			}
		}
