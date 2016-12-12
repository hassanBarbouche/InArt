package com.esprit.android.inart.adapters;


import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.etsy.android.grid.util.DynamicHeightTextView;
import com.esprit.android.inart.R;

import java.util.ArrayList;
import java.util.Random;

/***
 * ADAPTER
 */

public class SampleAdapterPhoto extends ArrayAdapter<String> {

    private static final String TAG = "SampleAdapter";

    static class ViewHolder {
        DynamicHeightTextView txtLineOne;
        DynamicHeightTextView txtLineTwo;
        Button btnGo;
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors2;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public SampleAdapterPhoto(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors2 = new ArrayList<Integer>();
        mBackgroundColors2.add(R.drawable.landscapes);
        mBackgroundColors2.add(R.drawable.portrait);
        mBackgroundColors2.add(R.drawable.bstract);
        mBackgroundColors2.add(R.drawable.blacknwhite);
        mBackgroundColors2.add(R.drawable.journalism);
        mBackgroundColors2.add(R.drawable.astral);
        mBackgroundColors2.add(R.drawable.people);
        mBackgroundColors2.add(R.drawable.musicphoto);
        mBackgroundColors2.add(R.drawable.other);


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_sample_photo, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView) convertView.findViewById(R.id.txt_line1);
            //vh.txtLineTwo = (DynamicHeightTextView) convertView.findViewById(R.id.txt_line2);


            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        int backgroundIndex = position >= mBackgroundColors2.size() ?
                position % mBackgroundColors2.size() : position;

        convertView.setBackgroundResource(mBackgroundColors2.get(backgroundIndex));

        Log.d(TAG, "getView position:" + position + " h:" + positionHeight);

        //vh.txtLineOne.setHeightRatio(positionHeight);
       // vh.txtLineOne.setText(getItem(position) + position);





        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
         //   sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}