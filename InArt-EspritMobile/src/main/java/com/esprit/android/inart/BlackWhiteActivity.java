/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esprit.android.inart;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.esprit.android.inart.Utils.Utils;
import com.esprit.android.inart.adapters.GoogleCardsSocialAdapter;
import com.esprit.android.inart.ui.BaseActivity;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BlackWhiteActivity extends BaseActivity implements
		OnDismissCallback {

	private static final int INITIAL_DELAY_MILLIS = 300;
	@Bind(R.id.image_f)
	ImageView mImageView;
	//public ImageLoader imageLoader = ImageLoader.getInstance();

	private GoogleCardsSocialAdapter mGoogleCardsAdapter;
	protected ImageLoader imageLoader;
	List<PhotoElement> mang;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		mang = new ArrayList<>() ;


		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_photographie);
		ButterKnife.bind(this);
		mBackground = mImageView;
		moveBackground();

		final ListView listView = (ListView) findViewById(R.id.list_view_photography);

		/////////////******///
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
		query.whereEqualTo("Genre", "Black and White");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> conThus, ParseException e) {
				if (e == null) {
					//Log.d("score", "Retrieved " + scoreList.size() + " scores");
					for (int i = 0; i < conThus.size(); i++) {
						ParseObject dong = conThus.get(i);
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


					mGoogleCardsAdapter = new GoogleCardsSocialAdapter(getApplicationContext(),mang
					);
					SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
							mGoogleCardsAdapter);
					swingBottomInAnimationAdapter.setAbsListView(listView);

					assert swingBottomInAnimationAdapter.getViewAnimator() != null;
					swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
							INITIAL_DELAY_MILLIS);

					listView.setClipToPadding(false);
					listView.setDivider(null);
					Resources r = getResources();
					int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
							8, r.getDisplayMetrics());
					listView.setDividerHeight(px);
					listView.setFadingEdgeLength(0);
					listView.setFitsSystemWindows(true);
					px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
							r.getDisplayMetrics());
					listView.setPadding(px, px, px, px);
					listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
					listView.setAdapter(swingBottomInAnimationAdapter);

				/*	SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
							new SwipeDismissAdapter(mGoogleCardsAdapter, this));
					swingBottomInAnimationAdapter.setAbsListView(listView);*/


				} else {
					Log.d("score", "Error: " + e.getMessage());
					Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

		///////***/////////





		//mGoogleCardsAdapter = new GoogleCardsSocialAdapter(this);
	/*	SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				new SwipeDismissAdapter(mGoogleCardsAdapter, this));
		swingBottomInAnimationAdapter.setAbsListView(listView);

		assert swingBottomInAnimationAdapter.getViewAnimator() != null;
		swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
				INITIAL_DELAY_MILLIS);

		listView.setClipToPadding(false);
		listView.setDivider(null);
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				8, r.getDisplayMetrics());
		listView.setDividerHeight(px);
		listView.setFadingEdgeLength(0);
		listView.setFitsSystemWindows(true);
		px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
				r.getDisplayMetrics());
		listView.setPadding(px, px, px, px);
		listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
		listView.setAdapter(swingBottomInAnimationAdapter);*/

	//	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	//	getSupportActionBar().setTitle("Google cards social");


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDismiss(@NonNull final ViewGroup listView,
			@NonNull final int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			mGoogleCardsAdapter.remove(mGoogleCardsAdapter.getItem(position));
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (Utils.hasHoneycomb()) {
			View demoContainerView = findViewById(R.id.image_f);
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
}