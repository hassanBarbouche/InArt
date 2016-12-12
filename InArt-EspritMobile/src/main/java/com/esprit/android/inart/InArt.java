package com.esprit.android.inart;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Hassan on 23/02/2014.
 */
public class InArt extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Parse.initialize(this, "vmjv3rzq0dljmz2DwtJ0hthF9Ryt4YwT3jGq93Ws", "4OkPaB77s6lzAaaBE5IWtKuaa9P9bjwIC5ONQwbW");
        ParseUser.enableRevocableSessionInBackground();
        // Parse.initialize(this,getString(R.string.parse_app_id),getString(R.string.parse_client_key));

      //  ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.

        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
