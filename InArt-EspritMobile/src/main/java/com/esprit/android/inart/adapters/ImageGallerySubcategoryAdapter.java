package com.esprit.android.inart.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.android.inart.FacebookLog;
import com.esprit.android.inart.MusicElement;
import com.esprit.android.inart.MyPhotosList;
import com.esprit.android.inart.PhotoElement;
import com.esprit.android.inart.R;
import com.esprit.android.inart.Utils.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ImageGallerySubcategoryAdapter extends BaseAdapter /*implements OnClickListener*/ {

    private static final int TYPE_ONE_COLUMN = 0;
    private static final int TYPE_TWO_COLUMNS = 1;
    private static final int TYPE_MAX_COUNT = TYPE_TWO_COLUMNS + 1;
	protected ImageLoader imageLoader;
    
	private LayoutInflater mInflater;
	private ArrayList<PhotoElement> mImageGallerySubcategories;
	private final boolean mIsLayoutOnTop;
	
	public ImageGallerySubcategoryAdapter(Context context,
										  ArrayList<PhotoElement> imageGallerySubcategories, boolean isLayoutOnTop) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageGallerySubcategories = imageGallerySubcategories;
		mIsLayoutOnTop = isLayoutOnTop;
	}
	
    @Override
    public int getItemViewType(int position) {
    	if ((position == mImageGallerySubcategories.size() / 2)
    			&& (mImageGallerySubcategories.size() % 2 == 1)) {
    		return TYPE_ONE_COLUMN;
    	} else {
    		return TYPE_TWO_COLUMNS;
    	}

    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

	@Override
	public int getCount() {
		return (mImageGallerySubcategories.size() / 2) + (mImageGallerySubcategories.size() % 2);
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder.OneColumnViewHolder oneColumnViewHolder;
		final ViewHolder.TwoColumnsViewHolder twoColumnsViewHolder;
		int type = getItemViewType(position);
		if (type == TYPE_ONE_COLUMN) {
			if (convertView == null) {
				 convertView = mInflater.inflate(R.layout.list_item_categories_one_column, parent, false);
				oneColumnViewHolder = new ViewHolder.OneColumnViewHolder();
				oneColumnViewHolder.image1 = (ImageView) convertView.findViewById(R.id.list_item_image_1);
				//oneColumnViewHolder.favoriteImage1 = (TextView) convertView.findViewById(R.id.list_item_favourite_1);
				oneColumnViewHolder.title1 = (TextView) convertView.findViewById(R.id.list_item_title_1);
				oneColumnViewHolder.numberOfImages1 = (TextView) convertView.findViewById(R.id.list_item_number_of_images_1);
				oneColumnViewHolder.layoutTopBottom1 = (ViewGroup) convertView.findViewById(R.id.layout_top_bottom_1);
				convertView.setTag(oneColumnViewHolder);
			} else {
				oneColumnViewHolder = (ViewHolder.OneColumnViewHolder) convertView.getTag();
			}
			PhotoElement model1 = mImageGallerySubcategories.get(position * 2);

			
			if (TextUtils.isEmpty(model1.name)) {
				oneColumnViewHolder.layoutTopBottom1.setVisibility(View.GONE);
			} else {
				oneColumnViewHolder.title1.setText(model1.name);
			}
			ImageUtil.displayImage(oneColumnViewHolder.image1, model1.url, null);
			oneColumnViewHolder.image1.setTag(position);
			
			//oneColumnViewHolder.favoriteImage1.setVisibility(View.GONE);
			oneColumnViewHolder.numberOfImages1.setVisibility(View.GONE);
			LayoutParams lp1 = (LayoutParams) oneColumnViewHolder.layoutTopBottom1.getLayoutParams();
			if (!mIsLayoutOnTop) {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			} else {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
			}
		} else if (type == TYPE_TWO_COLUMNS) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_categories_two_columns, parent, false);
				twoColumnsViewHolder = new ViewHolder.TwoColumnsViewHolder();
				twoColumnsViewHolder.image1 = (ImageView) convertView.findViewById(R.id.list_item_image_1);
				//twoColumnsViewHolder.favoriteImage1 = (TextView) convertView.findViewById(R.id.list_item_favourite_1);
				twoColumnsViewHolder.title1 = (TextView) convertView.findViewById(R.id.list_item_title_1);
				twoColumnsViewHolder.btndelete = (Button) convertView.findViewById(R.id.btndelete1);
				twoColumnsViewHolder.btndelete2 = (Button) convertView.findViewById(R.id.btndelete2);
				twoColumnsViewHolder.update1 = (Button) convertView.findViewById(R.id.settingbtn1);
				twoColumnsViewHolder.update2 = (Button) convertView.findViewById(R.id.settingbtn2);
				twoColumnsViewHolder.numberOfImages1 = (TextView) convertView.findViewById(R.id.list_item_number_of_images_1);
				twoColumnsViewHolder.layoutTopBottom1 = (ViewGroup) convertView.findViewById(R.id.layout_top_bottom_1);
				twoColumnsViewHolder.image2 = (ImageView) convertView.findViewById(R.id.list_item_image_2);
				//twoColumnsViewHolder.favoriteImage2 = (TextView) convertView.findViewById(R.id.list_item_favourite_2);
				twoColumnsViewHolder.title2 = (TextView) convertView.findViewById(R.id.list_item_title_2);
				twoColumnsViewHolder.numberOfImages2 = (TextView) convertView.findViewById(R.id.list_item_number_of_images_2);
				twoColumnsViewHolder.layoutTopBottom2 = (ViewGroup) convertView.findViewById(R.id.layout_top_bottom_2);
				convertView.setTag(twoColumnsViewHolder);


			} else {
				twoColumnsViewHolder = (ViewHolder.TwoColumnsViewHolder) convertView.getTag();
			}

			final AlertDialog.Builder alert = new AlertDialog.Builder(convertView.getContext());
			final AlertDialog.Builder alertupdate = new AlertDialog.Builder(convertView.getContext());
			final PhotoElement model1 = mImageGallerySubcategories.get(position * 2);
			alert.setMessage("Do you really want to delete this ?");
			alert.setTitle("Are you sure ?");

			alertupdate.setTitle("udpate your element");

			final EditText input = new EditText(convertView.getContext());
			//final EditText input_genre = new EditText(convertView.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			input.setHint("Element Name");
			input.setLayoutParams(lp);

			alertupdate.setView(input);

			final View finalConvertView = convertView;
			//alert.setView(edittext);

			alertupdate.setPositiveButton("Update", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
					query.whereEqualTo("ImageName",model1.name);
					Toast.makeText(finalConvertView.getContext(), "Photo succefully updated ", Toast.LENGTH_LONG).show();
					query.findInBackground(new FindCallback<ParseObject>() {
						public void done(List<ParseObject> conThus, ParseException e) {

							if (e == null) {
								System.out.println("noproblem");
								//Log.d("score", "Retrieved " + scoreList.size() + " scores");
								for (int i = 0; i < conThus.size(); i++) {
									ParseObject dong = conThus.get(i);
									System.out.println(dong.getString("ImageName"));
									//System.out.println(dong.getParseFile("ImageFile").toString());

										dong.put("ImageName",input.getText().toString());
									dong.saveInBackground();

									}




								}

							}
						}

					);
				}});

			alertupdate.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// what ever you want to do with No option.

				}
			});

			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
					query.whereEqualTo("ImageName",model1.name);
					Toast.makeText(finalConvertView.getContext(), "Photo succefully deleted ", Toast.LENGTH_LONG).show();
		query.findInBackground(new FindCallback<ParseObject>() {
						public void done(List<ParseObject> conThus, ParseException e) {

							if (e == null) {
								System.out.println("noproblem");
								//Log.d("score", "Retrieved " + scoreList.size() + " scores");
								for (int i = 0; i < conThus.size(); i++) {
									ParseObject dong = conThus.get(i);
									System.out.println(dong.getString("ImageName"));
									//System.out.println(dong.getParseFile("ImageFile").toString());
									try {
										dong.delete();
									} catch (ParseException e1) {

										e1.printStackTrace();
										System.out.println(e1.getMessage().toString());

									}
									dong.saveInBackground();



								}

							}
						}

			});
				}});

			alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// what ever you want to do with No option.

				}
			});

			twoColumnsViewHolder.update1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					alertupdate.show();


					//View V = finalConvertView;


				}
			});



			twoColumnsViewHolder.btndelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					alert.show();


					//View V = finalConvertView;


				}
			});

			twoColumnsViewHolder.btndelete2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					alert.show();


					//View V = finalConvertView;



				}
			});

// Retrieve the object by id

			if (TextUtils.isEmpty(model1.name)) {
				twoColumnsViewHolder.layoutTopBottom1.setVisibility(View.GONE);
			} else {
				twoColumnsViewHolder.title1.setText(model1.name);
			}
			ImageUtil.displayImage(twoColumnsViewHolder.image1, model1.url, null);
			
			PhotoElement model2 = mImageGallerySubcategories.get(position * 2 + 1);
			if (TextUtils.isEmpty(model2.name)) {
				twoColumnsViewHolder.layoutTopBottom2.setVisibility(View.GONE);
			} else {
				twoColumnsViewHolder.title2.setText(model2.name);
			}
			ImageUtil.displayImage(twoColumnsViewHolder.image2, model2.url, null);
			
			twoColumnsViewHolder.image1.setTag(position);
			twoColumnsViewHolder.image2.setTag(position);
			
			//twoColumnsViewHolder.favoriteImage1.setVisibility(View.GONE);
			twoColumnsViewHolder.numberOfImages1.setVisibility(View.GONE);
			//twoColumnsViewHolder.favoriteImage2.setVisibility(View.GONE);
			twoColumnsViewHolder.numberOfImages2.setVisibility(View.GONE);
			LayoutParams lp1 = (LayoutParams) twoColumnsViewHolder.layoutTopBottom1.getLayoutParams();
			LayoutParams lp2 = (LayoutParams) twoColumnsViewHolder.layoutTopBottom2.getLayoutParams();
			if (!mIsLayoutOnTop) {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			} else {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
				lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
			}
		}
		return convertView;
	}
	
	/* We are not using favourite image here. If you really want to use it,
	 * remove commented lines related to favourite image. 
	 */
	private static class ViewHolder {
		public static class OneColumnViewHolder {
			public ImageView image1;
			//public TextView favoriteImage1;
			public TextView title1;
			public TextView numberOfImages1;
			public ViewGroup layoutTopBottom1;
		}
		
		private static class TwoColumnsViewHolder {
			public ImageView image1;
			public Button btndelete;
			public  Button btndelete2;
			public Button update1,update2;
			//public TextView favoriteImage1;
			public TextView title1;
			public TextView numberOfImages1;
			public ViewGroup layoutTopBottom1;

			public ImageView image2;
			//public TextView favoriteImage2;
			public TextView title2;
			public TextView numberOfImages2;
			public ViewGroup layoutTopBottom2;
		}
	}
}
