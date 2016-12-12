package com.esprit.android.inart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.android.inart.SpaceElement;
import com.esprit.android.inart.SpaceMusicActivity;
import com.esprit.android.inart.R;
import com.esprit.android.inart.models.DummyModel;
import com.esprit.android.inart.Utils.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;

public class DragAndDropTravelAndSocialAdapter extends BaseAdapter implements
		Swappable {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<SpaceElement> mDummyModelList;
	private String category;

	public DragAndDropTravelAndSocialAdapter(Context context,
											 ArrayList<SpaceElement> dummyModelList, String category) {
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDummyModelList = dummyModelList;
		this.category = category;
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
					.equals(SpaceMusicActivity.DRAG_AND_DROP_TRAVEL)) {
				convertView = mInflater.inflate(
						R.layout.list_item_drag_and_drop_travel, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.drag_and_drop_travel_image);
				holder.text = (TextView) convertView
						.findViewById(R.id.drag_and_drop_travel_text);

				holder.desc = (TextView) convertView
						.findViewById(R.id.desc);


				convertView.setTag(holder);
			} else {
				convertView = mInflater.inflate(
						R.layout.list_item_drag_and_drop_social, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_drag_and_drop_social_image);
				holder.text = (TextView) convertView
						.findViewById(R.id.item_drag_and_drop_social_name);
				holder.place = (TextView) convertView
						.findViewById(R.id.item_drag_and_drop_social_place);
				convertView.setTag(holder);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SpaceElement dm = mDummyModelList.get(position);

		if (category
				.equals(SpaceMusicActivity.DRAG_AND_DROP_TRAVEL)) {
			ImageUtil.displayImage(holder.image, dm.url, null);
			holder.text.setText(dm.name);
			holder.desc.setText(dm.description);


		} else {
			ImageUtil.displayRoundImage(holder.image, dm.url, null);
			holder.text.setText(dm.name);

		}
		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public/* Roboto */TextView text;
		public/* Roboto */TextView subtext;
		public/* Fontello */TextView icon;
		public/* Roboto */TextView rating;
		public/* Roboto */TextView place;
		public/* Roboto */TextView desc;
	}
}
