package com.esprit.android.inart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.esprit.android.inart.adapters.DragAndDropTravelAndSocialAdapter;
import com.esprit.android.inart.ui.BaseActivity;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SpaceCinemaActivity extends BaseActivity {

	public static final String LIST_VIEW_OPTION = "com.esprit.android.inart.DragAndDropTravelAndSocialActivity";
	public static final String DRAG_AND_DROP_TRAVEL = "drag.and.drop.travel";
	public static final String DRAG_AND_DROP_SOCIAL = "drag.and.drop.social";
	protected ImageLoader imageLoader;
	List<SpaceElement> mang;
	private DynamicListView mDynamicListView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_views);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));

		mDynamicListView = (DynamicListView) findViewById(R.id.dynamic_listview);
		mDynamicListView.setDividerHeight(0);

		String category = DRAG_AND_DROP_TRAVEL;
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras
						.containsKey(CategoriesListViewActivity.LIST_VIEW_OPTION_DRAG_AND_DROP)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras
						.getString(
								CategoriesListViewActivity.LIST_VIEW_OPTION_DRAG_AND_DROP,
								DRAG_AND_DROP_TRAVEL);
			} else {
				category = extras
						.getString(CategoriesListViewActivity.LIST_VIEW_OPTION_DRAG_AND_DROP);
			}
		}
		if (category.equals(DRAG_AND_DROP_TRAVEL)) {
			setUpDragAndDrop();
		}
		Toast.makeText(this, "Long press an item to start dragging",
				Toast.LENGTH_SHORT).show();


	}



	private void setUpDragAndDrop() {
		mang = new ArrayList<SpaceElement>() ;

		final ParseQuery<ParseObject> query = ParseQuery.getQuery("Space");
		query.whereEqualTo("Genre", "Cinema");
		//query.whereEqualTo("Genre", "Landscapes");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> conThus, ParseException e) {
				if (e == null) {
					//Log.d("score", "Retrieved " + scoreList.size() + " scores");
					for (int i = 0; i < conThus.size(); i++) {
						ParseObject dong = conThus.get(i);
						//System.out.println(dong.getParseFile("ImageFile").toString());
						mang.add(new SpaceElement(
								dong.getString("SpaceName"),
								dong.getString("Description"),
								dong.getParseFile("SpaceImage"),
								dong.getParseFile("SpaceImage").getUrl(),
								dong.getString("Owner"),
								dong.getString("Address"),
								dong.getString("Genre")



						));
					}
					;
					final DragAndDropTravelAndSocialAdapter adapter = new DragAndDropTravelAndSocialAdapter(
							getBaseContext(), (ArrayList<SpaceElement>) mang,
							DRAG_AND_DROP_TRAVEL);
					mDynamicListView.setAdapter(adapter);
					mDynamicListView.enableDragAndDrop();
					TouchViewDraggableManager tvdm = new TouchViewDraggableManager(
							R.id.drag_and_drop_travel_icon);
					mDynamicListView.setDraggableManager(new TouchViewDraggableManager(
							R.id.icon));
					mDynamicListView.setDraggableManager(tvdm);
					mDynamicListView
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
														View view, int position, long id) {

									//HashMap<String, String> map = (HashMap<String, String>) mDynamicListView.getItemAtPosition(position);
									SpaceElement ev = (SpaceElement) (mDynamicListView.getItemAtPosition(position));
									//query.whereEqualTo("Name", ev.adress);

									//mDynamicListView.startDragging(position);
									System.out.println(ev.adress.toString());
									//Toast.makeText(ListAddress.this, map.get("adresse"), Toast.LENGTH_LONG).show();
									Intent i = new Intent(SpaceCinemaActivity.this, MapsActivity.class);
									//String item = map.get("adresse");
									i.putExtra("adresse", ev.adress.toString());
									i.putExtra("description", ev.description.toString());
									i.setAction(Intent.ACTION_VIEW);
									startActivity(i);
									//return true;
								}
							});


				} else {
					Log.d("score", "Error: " + e.getMessage());
					Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});



				}







			private void setUpDragAndDropSocial() {
			/*	final DragAndDropTravelAndSocialAdapter adapter = new DragAndDropTravelAndSocialAdapter(
						this, DummyContent.getDummyModelList(), DRAG_AND_DROP_SOCIAL);
				mDynamicListView.setAdapter(adapter);
				mDynamicListView.enableDragAndDrop();
				TouchViewDraggableManager tvdm = new TouchViewDraggableManager(
						R.id.drag_and_drop_travel_icon);
				mDynamicListView.setDraggableManager(new TouchViewDraggableManager(
						R.id.icon));
				mDynamicListView.setDraggableManager(tvdm);
				mDynamicListView
						.setOnItemLongClickListener(new OnItemLongClickListener() {

							@Override
							public boolean onItemLongClick(AdapterView<?> parent,
														   View view, int position, long id) {
								mDynamicListView.startDragging(position);
								return true;
							}
						});*/
			}
		}
