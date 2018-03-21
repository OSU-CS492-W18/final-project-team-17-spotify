package com.cs492.finalproject.spotifyapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
/**
 * Created by Sophia on 3/20/2018.
 */

public class SpotifyTrackTask extends AsyncTaskLoader<String>{

    private final String TAG = SpotifyTrackTask.class.getSimpleName();

    private String mCachedData;
    private String mToken;

    public SpotifyTrackTask(Context context, String token) {
        super(context);
        mToken = token;
    }

    @Override
    protected void onStartLoading() {
        if (mCachedData != null) {
            Log.d(TAG, "using cached track");
            deliverResult(mCachedData);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    //taken from notes
    public String loadInBackground() { //similar to doInBackground; loads data in background thread
        String trackJSON = null;
        Log.d(TAG, "Loading track data");
        try {
            trackJSON = NetworkUtils.doHTTPGet(SpotifyUtils.buildTrackUrl(mTrackID), mToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trackJSON;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        mCachedData = data;
        super.deliverResult(data);
    }
}
