package com.esprit.android.inart.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.android.inart.News;
import com.esprit.android.inart.R;
import com.esprit.android.inart.models.DummyModel;
import com.esprit.android.inart.Utils.ImageUtil;

import java.util.List;

public class GoogleCardsTravelAdapter extends BaseAdapter
		implements OnClickListener {

	private LayoutInflater mInflater;
	List<News> news;

	public GoogleCardsTravelAdapter(Context context, List<News> items) {
		//super(context, 0, items);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.news = items;
	}

	public void update(List<News> news) {
		this.news = news;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public Object getItem(int arg0) {
		return news.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_travel, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_travel_image);
			holder.desc = (TextView) convertView
					.findViewById(R.id.newsdesc);

			holder.title = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_title);

			holder.explore = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_explore);
			holder.share = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_share);
			holder.explore.setOnClickListener(this);
			holder.share.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		News item = (News) getItem(position);
		String content = Html.fromHtml(item.getContent()).toString().trim();
		String description = Html.fromHtml(item.getDescription()).toString().trim();
		ImageUtil.displayImage(holder.image, content, null);
		holder.title.setText(item.getTitle());
		holder.desc.setText(description);
		holder.explore.setTag(position);
		holder.share.setTag(position);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView categoryName;
		public TextView title;
		public TextView text;
		public TextView explore;
		public TextView share;
		public TextView desc;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.list_item_google_cards_travel_explore:
			// click on explore button
			break;
		case R.id.list_item_google_cards_travel_share:
			// click on share button
			break;
		}
	}
}
