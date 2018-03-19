package com.cs492.finalproject.spotifyapp;

/**
 * Created by tasniakabir on 3/19/18.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;


public class SpotifyCategoryTask extends AsyncTaskLoader<String> {

    private final String TAG = SpotifyCategoryTask.class.getSimpleName();

    private String mCachedData;
    private String mToken;

    public SpotifyCategoryTask(Context context, String token) {
        super(context);
        mToken = token;
    }

    @Override
    protected void onStartLoading() {
        if (mCachedData != null) {
            Log.d(TAG, "using cached forecast");
            deliverResult(mCachedData);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    //taken from notes
    public String loadInBackground() { //similar to doInBackground; loads data in background thread
        String categoryJSON = null;
        Log.d(TAG, "Loading category data");
        try {
            categoryJSON = NetworkUtils.doHTTPGet(SpotifyUtils.buildCategoryUrl(), mToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryJSON;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mCachedData = data;
        super.deliverResult(data);
    }
}