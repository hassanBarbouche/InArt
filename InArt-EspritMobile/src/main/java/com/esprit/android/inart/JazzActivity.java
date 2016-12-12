package com.esprit.android.inart;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.esprit.android.inart.Utils.DummyContent;
import com.esprit.android.inart.adapters.DefaultAdapter;
import com.esprit.android.inart.adapters.SwipeToDissmissTravelAndSocialAdapter;
import com.esprit.android.inart.ui.BaseActivity;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class JazzActivity extends BaseActivity {

	public static final String SWIPE_TO_DISSMISS_TRAVEL = "travel";
	public static final String SWIPE_TO_DISSMISS_SOCIAL = "social";
	List<MusicElement> mang;

	private DynamicListView mDynamicListView;
	protected ImageLoader imageLoader;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mang = new ArrayList<MusicElement>() ;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_views_music);

		mDynamicListView = (DynamicListView) findViewById(R.id.dynamic_listview);
		String category = SWIPE_TO_DISSMISS_TRAVEL;
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(CategoriesListViewActivity.LIST_VIEW_OPTION_SWIPE_TO_DISSMISS)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras
						.getString(
								CategoriesListViewActivity.LIST_VIEW_OPTION_SWIPE_TO_DISSMISS,
								SWIPE_TO_DISSMISS_TRAVEL);
			} else {
				category = extras
						.getString(CategoriesListViewActivity.LIST_VIEW_OPTION_SWIPE_TO_DISSMISS);
			}
		}

		if (category.equals(SWIPE_TO_DISSMISS_TRAVEL)) {
			setUpSwipeToDissmissTravel();
		} else if (category.equals(SWIPE_TO_DISSMISS_SOCIAL)) {
			mDynamicListView
					.setBackgroundResource(R.drawable.drag_and_drop_background_image);
			setUpSwipeToDissmissSocial();
		} else {
			setUpSwipeToDissmiss();
		}
	//	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	//	getSupportActionBar().setTitle("Swipe to Dissmiss");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setUpSwipeToDissmiss() {
		final DefaultAdapter adapter = new DefaultAdapter(this,
				DummyContent.getDummyModelList(), false);
		SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(
				adapter, this, new OnDismissCallback() {
					@Override
					public void onDismiss(@NonNull final ViewGroup listView,
							@NonNull final int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							adapter.remove(position);
						}
					}
				});
		swipeUndoAdapter.setAbsListView(mDynamicListView);
		mDynamicListView.setAdapter(swipeUndoAdapter);
		mDynamicListView.enableSimpleSwipeUndo();
	}

	private void setUpSwipeToDissmissTravel() {
	/*	final SwipeToDissmissTravelAndSocialAdapter adapter = new SwipeToDissmissTravelAndSocialAdapter(
				this, DummyContent.getDummyModelSwipeToDissmissTravelList(),
				ListMusicActivity.SWIPE_TO_DISSMISS_TRAVEL);
		SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(
				adapter, this, new OnDismissCallback() {
					@Override
					public void onDismiss(@NonNull final ViewGroup listView,
							@NonNull final int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							adapter.remove(position);
						}
					}
				});
		swipeUndoAdapter.setAbsListView(mDynamicListView);
		mDynamicListView.setAdapter(swipeUndoAdapter);
		mDynamicListView.enableSimpleSwipeUndo();*/




		final ParseQuery<ParseObject> query = ParseQuery.getQuery("Music");
		query.whereEqualTo("Genre", "Jazz");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> conThus, ParseException e) {
				if (e == null) {
					//Log.d("score", "Retrieved " + scoreList.size() + " scores");
					for (int i = 0; i < conThus.size(); i++) {
						ParseObject dong = conThus.get(i);
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
					;
					final SwipeToDissmissTravelAndSocialAdapter adapter = new SwipeToDissmissTravelAndSocialAdapter(
							getBaseContext(), (ArrayList<MusicElement>) mang,
							JazzActivity.SWIPE_TO_DISSMISS_TRAVEL);
					SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(
							adapter, getBaseContext(), new OnDismissCallback() {
						@Override
						public void onDismiss(@NonNull final ViewGroup listView,
											  @NonNull final int[] reverseSortedPositions) {
							for (int position : reverseSortedPositions) {
								adapter.remove(position);
							}
						}
					});
					swipeUndoAdapter.setAbsListView(mDynamicListView);
					mDynamicListView.setAdapter(swipeUndoAdapter);
					mDynamicListView.enableSimpleSwipeUndo();


				} else {
					Log.d("score", "Error: " + e.getMessage());
					Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private void setUpSwipeToDissmissSocial() {
		/*final SwipeToDissmissTravelAndSocialAdapter adapter = new SwipeToDissmissTravelAndSocialAdapter(
				this, DummyContent.getDummyModelSwipeToDissmissSocialList(),
				ListMusicActivity.SWIPE_TO_DISSMISS_SOCIAL);
		SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(
				adapter, this, new OnDismissCallback() {
					@Override
					public void onDismiss(@NonNull final ViewGroup listView,
							@NonNull final int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							adapter.remove(position);
						}
					}
				});
		swipeUndoAdapter.setAbsListView(mDynamicListView);
		mDynamicListView.setAdapter(swipeUndoAdapter);
		mDynamicListView.enableSimpleSwipeUndo();*/
	}
}
