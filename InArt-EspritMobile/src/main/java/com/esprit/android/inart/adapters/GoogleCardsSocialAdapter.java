package com.esprit.android.inart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.android.inart.Utils.ImageUtil;
import com.esprit.android.inart.PhotoElement;
import com.esprit.android.inart.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class GoogleCardsSocialAdapter extends ArrayAdapter<PhotoElement>
		implements OnClickListener {

	private LayoutInflater mInflater;
	public String ownerurl;
	public static String PREFERENCE_FILENAME = "reporting_app";
	public  ViewHolder holder;

	public GoogleCardsSocialAdapter(Context context, List<PhotoElement> items) {
		super(context, 0, items);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//final  ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_social, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_social_image);
			holder.profileImage = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_social_profile_image);
			holder.username = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_name);
			holder.place = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_place);
			holder.text = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_text);
			holder.like = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_like);
			holder.follow = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_social_follow);
			holder.likenumber = (TextView) convertView
					.findViewById(R.id.like_number);
			holder.like.setOnClickListener(this);
			holder.follow.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/*final ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");

		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> conThus, ParseException e) {
				if (e == null) {
					//Log.d("score", "Retrieved " + scoreList.size() + " scores");
					for (int i = 0; i < conThus.size(); i++) {
						ParseObject dong = conThus.get(i);
						System.out.println(dong.getParseFile("ImageFile").toString());

						ImageUtil.displayRoundImage(holder.profileImage,  dong.getParseFile("ImageFile").getUrl(),
								null);
						ImageUtil.displayImage(holder.image, dong.getParseFile("ImageFile").getUrl(), null);
						holder.username.setText("@" + dong.getString("ImageName"));
						holder.place.setText("from Oklahoma");
						holder.text.setText(R.string.lorem_ipsum_short);
						//holder.like.setTag(position);
						//holder.follow.setTag(position);


					}}}});*/

		ParseQuery<ParseUser> query = ParseUser.getQuery();




					PhotoElement item = getItem(position);


		query.whereEqualTo("username", item.Owner);
		query.findInBackground(new FindCallback<ParseUser>() {


			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				for (int i = 0; i < objects.size(); i++) {
					ImageUtil.displayRoundImage(holder.profileImage, objects.get(i).getString("ImageUrl"),
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


		//ImageUtil.displayRoundImage(holder.profileImage, item.url, null);
		ImageUtil.displayImage(holder.image, item.url, null);
					holder.username.setText("@" + item.Owner);
					holder.place.setText(item.name);
					holder.text.setText(R.string.lorem_ipsum_short);
					holder.like.setTag(position);
		            holder.likenumber.setText(item.likes+"");
					holder.follow.setTag(position);

					return convertView;
				}

				private static class ViewHolder {
					public ImageView profileImage;
					public ImageView image;
					public TextView username;
					public TextView place;
					public TextView text;
					public TextView like;
					public TextView follow;
					public TextView likenumber;
				}

				@Override
				public void onClick (View v){
					// TODO Auto-generated method stub
					int possition = (Integer) v.getTag();
					switch (v.getId()) {

						case R.id.list_item_google_cards_social_like:


							int y = Integer.parseInt(holder.likenumber.getText().toString())+1;
							holder.likenumber.setText(y+"");
							// click on like button
							break;
						case R.id.list_item_google_cards_social_follow:
							// click on follow button
							break;

					}
				}
			}
