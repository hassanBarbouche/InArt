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

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.esprit.android.inart.Utils.RssParser;
import com.esprit.android.inart.adapters.GoogleCardsTravelAdapter;
import com.esprit.android.inart.Utils.DummyContent;
import com.esprit.android.inart.ui.BaseActivity;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MusicNewsActivity extends BaseActivity implements
		OnDismissCallback {

	private static final int INITIAL_DELAY_MILLIS = 300;
	protected ImageLoader imageLoader;
	AsyncTask<Void, Void, List> a = null;
	@Bind(R.id.image_d)
	ImageView mImageView;


	private GoogleCardsTravelAdapter mGoogleCardsAdapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		ButterKnife.bind(this);
		mBackground = mImageView;
		moveBackground();

		ListView listView = (ListView) findViewById(R.id.list_view);

		 mGoogleCardsAdapter = new GoogleCardsTravelAdapter(this,
				new ArrayList<News>());
		final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				new SwipeDismissAdapter(mGoogleCardsAdapter, this));
		swingBottomInAnimationAdapter.setAbsListView(listView);

		assert swingBottomInAnimationAdapter.getViewAnimator() != null;
		swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
				INITIAL_DELAY_MILLIS);
		a = new AsyncTask<Void, Void, List>() {
			@Override
			protected List doInBackground(Void... params) {
				ArrayList<News> res = new ArrayList<News>();
				try {
					URL url = new URL("http://www.music-news.com/rss/UK/news");
					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					RssParser parser = new RssParser();
					try {
						return parser.parse(urlConnection.getInputStream());
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return res;
			}

			@Override
			protected void onPostExecute(List result) {
				mGoogleCardsAdapter.update(result);
				//ListAdapter adapter = new ListAdapter(getApplicationContext(), R.layout.single_list_item, mang);
				System.out.println();
				//lv.setAdapter(adapter);
				//  ListView lv = null;


			}
		};
		a.execute();

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

		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setTitle("Google cards travel");
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
			//mGoogleCardsAdapter.remove(mGoogleCardsAdapter.getItem(position));
		}
	}
}