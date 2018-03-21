package com.cs492.finalproject.spotifyapp;

/**
 * Created by Sophia on 3/20/2018.
 */

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class TrackActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>, TrackItemAdapter.OnTrackItemClickListener {

    private RecyclerView mTrackListRV;
    private TrackItemAdapter mTrackItemAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_track);
                mTrackListRV = (RecyclerView)findViewById(R.id.rv_track);

                mTrackListRV.setLayoutManager(new LinearLayoutManager(this));
                mTrackListRV.setHasFixedSize(true);

                mTrackItemAdapter = new TrackItemAdapter(this);
                mTrackListRV.setAdapter(mTrackItemAdapter);

                mTrackListRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

                Intent intent = getIntent();
                String token = intent.getStringExtra("token");
                String trackURL = intent.getStringExtra(SpotifyUtils.TrackItem.EXTRA_TRACK_ITEM);
                loadTracks(token, trackURL, true);
        }

        public void loadTracks(String token, String trackURL, boolean initialLoad) {
                Bundle loaderArgs = new Bundle();
                loaderArgs.putString("token", token);
                loaderArgs.putString("trackURL", trackURL);
                LoaderManager loaderManager = getLoaderManager();
                if (initialLoad) {
                loaderManager.initLoader(2, loaderArgs, this);
                } else {
                loaderManager.restartLoader(2, loaderArgs, this);
                }
        }

            /*
            * This for the http request stuff
            */

        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                String token = null;
                String trackURL = null;
                if (args != null) {
                token = args.getString("token");
                trackURL = args.getString("trackURL");
                }
                return new SpotifyTrackTask(this, token, trackURL);
                }

        @Override
        public void onLoadFinished(android.content.Loader<String> loader, String data) {
                if (data != null) {
                ArrayList<SpotifyUtils.TrackItem> trackItems = SpotifyUtils.parseTrackJSON(data);
                mTrackItemAdapter.updateTrackData(trackItems);
                mTrackListRV.setVisibility(View.VISIBLE);
                Log.d("TrackActivity", "Load finished!");
                }
        }

        @Override
        public void onLoaderReset(android.content.Loader<String> loader) {

        }

        @Override
        public void onTrackItemClick(String trackID) {
                Intent intent = new Intent(this, TrackActivity.class);
                intent.putExtra(SpotifyUtils.TrackItem.EXTRA_TRACK_ITEM, trackID);
                this.startActivity(intent);
        }

}
