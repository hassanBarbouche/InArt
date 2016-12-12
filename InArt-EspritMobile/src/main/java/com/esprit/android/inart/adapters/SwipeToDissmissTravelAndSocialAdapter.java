package com.esprit.android.inart.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.android.inart.MusicElement;
import com.esprit.android.inart.PhotoElement;
import com.esprit.android.inart.R;
import com.esprit.android.inart.ListMusicActivity;
import com.esprit.android.inart.models.DummyModel;
import com.esprit.android.inart.Utils.ImageUtil;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;
import com.nhaarman.listviewanimations.util.Swappable;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwipeToDissmissTravelAndSocialAdapter extends BaseAdapter
		implements Swappable, UndoAdapter, OnDismissCallback {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<MusicElement> mDummyModelList;
	private String category;
	private MediaPlayer mediaplayer;

	public SwipeToDissmissTravelAndSocialAdapter(Context context,
												 ArrayList<MusicElement> dummyModelList, String cat) {


		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDummyModelList = dummyModelList;
		category = cat;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public int getCount() {
		return mDummyModelList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDummyModelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDummyModelList.get(position).getId();
	}

	@Override
	public void swapItems(int positionOne, int positionTwo) {
		Collections.swap(mDummyModelList, positionOne, positionTwo);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			if (category
					.equals(ListMusicActivity.SWIPE_TO_DISSMISS_TRAVEL)) {
				convertView = mInflater.inflate(
						R.layout.list_item_swipe_to_dissmiss_travel, parent,
						false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_swip_to_dissmiss_travel_image);
				holder.travelHeader = (TextView) convertView
						.findViewById(R.id.item_swip_to_dissmiss_travel_header);
				holder.travelSubheader = (TextView) convertView
						.findViewById(R.id.item_swip_to_dissmiss_travel_subheader);
			} else {
				convertView = mInflater.inflate(
						R.layout.list_item_swipe_to_dissmiss_social, parent,
						false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.swipe_to_dissmiss_lv_social_image);
				holder.name = (TextView) convertView
						.findViewById(R.id.swipe_to_dissmiss_lv_social_name);
				holder.text = (TextView) convertView
						.findViewById(R.id.swipe_to_dissmiss_lv_social_text);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (category
				.equals(ListMusicActivity.SWIPE_TO_DISSMISS_TRAVEL)) {

			MusicElement dm = mDummyModelList.get(position);

			ImageUtil.displayImage(holder.image, dm.url_photo, null);
			holder.travelHeader.setText(dm.name);
			holder.travelSubheader.setText("By "+dm.Owner);
			// holder.travelHeader.setText(dm.getText());
			return convertView;
		} else {
			MusicElement dm = mDummyModelList.get(position);

			ImageUtil.displayRoundImage(holder.image, dm.url_photo, null);
			holder.name.setText(dm.name);
			return convertView;
		}
	}

	private static class ViewHolder {
		public ImageView image;
		public/* Roboto */TextView text;
		public/* Roboto */TextView name;
		public/* Roboto */TextView travelHeader;
		public/* Roboto */TextView travelSubheader;
		public/* Fontello */TextView icon;
	}

	private static class ViewHolder2 {
		public ImageView image2;
		public/* Roboto */TextView text2;
		public/* Roboto */TextView name2;
		public/* Roboto */TextView travelHeader2;
		public/* Roboto */TextView travelSubheader2;
		public/* Fontello */TextView icon2;
	}

	@Override
	@NonNull
	public View getUndoClickView(@NonNull View view) {

		if (category
				.equals(ListMusicActivity.SWIPE_TO_DISSMISS_TRAVEL)) {
			return view.findViewById(R.id.undo_button_travel);
		} else if (category
				.equals(ListMusicActivity.SWIPE_TO_DISSMISS_SOCIAL))
			return view.findViewById(R.id.undo_button_social);
		else {
			return view.findViewById(R.id.undo_button);
		}
	}

	@Override
	@NonNull
	public View getUndoView(final int position, final View convertView,
			@NonNull final ViewGroup parent) {
		//final ViewHolder2 hold;
		final MediaPlayer mediaPlayer = new MediaPlayer();
		View view = convertView;
		TextView tex;
		 final FABProgressCircle fabProgressCircle;
		final View Startplay,Stopplay;
		final ImageView ownimage;
		if (view == null) {
			if (category
					.equals(ListMusicActivity.SWIPE_TO_DISSMISS_TRAVEL)) {




				view = LayoutInflater.from(mContext).inflate(
						R.layout.list_item_undo_view_travel, parent, false);
				//hold = new ViewHolder2();
				tex=(TextView) view
						.findViewById(R.id.photodescription);
				fabProgressCircle = (FABProgressCircle) view.findViewById(R.id.fabProgressCirclerec);


				//ParseQuery query = new ParseQuery("Music");
				//query.orderByDescending("createdAt");


				Startplay = (View) view.findViewById(R.id.fabplay);
				Stopplay = (View) view.findViewById(R.id.fabstop);
				ownimage=(ImageView) view
						.findViewById(R.id.imageView4);
				final MusicElement dm2 = mDummyModelList.get(position);
				tex.setText(dm2.description);
				Stopplay.setVisibility(View.GONE);
				Startplay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Startplay.setVisibility(View.GONE);
						Stopplay.setVisibility(View.VISIBLE);
						//llsave.setVisibility(View.GONE);
						fabProgressCircle.show();
						ParseFile descr = dm2.music_element;
						if (descr != null) {
							String audioFileURL = descr.getUrl();

							mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

							try {
								mediaPlayer.setDataSource(audioFileURL);
								mediaPlayer.prepare();
								mediaPlayer.start();

							} catch (IllegalArgumentException e1) {
								System.out.println(e1.getMessage());e1.printStackTrace();
							} catch (SecurityException e1) {
								System.out.println(e1.getMessage());e1.printStackTrace();
							} catch (IllegalStateException e1) {
								System.out.println(e1.getMessage());e1.printStackTrace();
							} catch (IOException e) {
								System.out.println(e.getMessage());e.printStackTrace();
							}
						}
						if (descr == null){

							System.out.println("null");
						}


					}
				});

				Stopplay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Startplay.setVisibility(View.VISIBLE);
						Stopplay.setVisibility(View.GONE);
						//llsave.setVisibility(View.GONE);
						fabProgressCircle.hide();

						mediaPlayer.stop();






					}});


						ParseQuery<ParseUser> query = ParseUser.getQuery();




						//PhotoElement item = getItem(position);


						query.whereEqualTo("username", dm2.Owner);
						query.findInBackground(new FindCallback<ParseUser>() {


							@Override
							public void done(List<ParseUser> objects, ParseException e) {
								for (int i = 0; i < objects.size(); i++) {
									ImageUtil.displayRoundImage(ownimage, objects.get(i).getString("ImageUrl"),
											null);

								}
							}

		/*	@Override
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					//ownerurl = object.getParseFile("UserImage").getUrl();
					ImageUtil.displayRoundImage(holder.profileImage, object.getParseFile("UserImage").getUrl(),
							null);
				} else {
					Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				}

			}*/


						});


					}

					else if(category
							.equals(ListMusicActivity.SWIPE_TO_DISSMISS_SOCIAL))

					{
						view = LayoutInflater.from(mContext).inflate(
								R.layout.list_item_undo_view_social, parent, false);
					}

					else

					{
						view = LayoutInflater.from(mContext).inflate(
								R.layout.list_item_undo_view, parent, false);
					}
				}
				return view;
	}

	@Override
	public void onDismiss(@NonNull final ViewGroup listView,
			@NonNull final int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			remove(position);
		}
	}

	public void remove(int position) {
		mDummyModelList.remove(position);
	}
}
