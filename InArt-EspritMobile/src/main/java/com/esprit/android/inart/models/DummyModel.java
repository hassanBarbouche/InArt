package com.esprit.android.inart.models;

public class DummyModel {
	
	private long mId;
	private String mImageURL;
	private String mText;
	private int mIconRes;
	private int dd;

	public DummyModel() {
	}

	public DummyModel(long id, String imageURL, String text, int iconRes) {
		mId = id;
		mImageURL = imageURL;
		mText = text;
		mIconRes = iconRes;
	}
	public DummyModel(long id, int map) {

		mId = id;
		dd = map;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(String imageURL) {
		mImageURL = imageURL;
	}

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		mText = text;
	}

	public int getIconRes() {
		return mIconRes;
	}
	public int getDd() {
		return dd;
	}

	public void setIconRes(int iconRes) {
		mIconRes = iconRes;
	}

	@Override
	public String toString() {
		return mText;
	}
}
