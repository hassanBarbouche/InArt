package com.esprit.android.inart.adapters;

import java.util.ArrayList;

public class SampleData2 {

    public static final int SAMPLE_DATA_ITEM_COUNT = 6;

    public static ArrayList<String> generateSampleData() {
        final ArrayList<String> data = new ArrayList<String>(SAMPLE_DATA_ITEM_COUNT);

        for (int i = 0; i < SAMPLE_DATA_ITEM_COUNT; i++) {
            data.add(" ");
        }

        return data;
    }

}
