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
    private PlaylistItemAdapter mTrackItemAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.track_playlist);
                mTrackListRV = (RecyclerView)findViewById(R.id.rv_track);

                mTrackListRV.setLayoutManager(new LinearLayoutManager(this));
                mTrackListRV.setHasFixedSize(true);

                mTrackItemAdapter = new TrackItemAdapter(this);
                mTrackListRV.setAdapter(mTrackItemAdapter);

                mTrackListRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

                Intent intent = getIntent();
                String token = intent.getStringExtra("token");
                String playlistID = intent.getStringExtra(SpotifyUtils.PlaylistItem.EXTRA_PLAYLIST_ITEM);
                loadTracks(token, playlistID, true);
                }

        public void loadTracks(String token, String playlistID, boolean initialLoad) {
                Bundle loaderArgs = new Bundle();
                loaderArgs.putString("token", token);
                loaderArgs.putString("playlistID", playlistID);
                LoaderManager loaderManager = getLoaderManager();
                if (initialLoad) {
                loaderManager.initLoader(1, loaderArgs, this);
                } else {
                loaderManager.restartLoader(1, loaderArgs, this);
                }
                }

            /*
            * This for the http request stuff
            */

        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                String token = null;
                String playlistID = null;
                if (args != null) {
                token = args.getString("token");
                playlistID  = args.getString("playlistID");
                }
                return new SpotifyPlaylistTask(this, token, playlistID);
                }

        @Override
        public void onLoadFinished(android.content.Loader<String> loader, String data) {
                if (data != null) {
                ArrayList<SpotifyUtils.TrackItem> trackItems = SpotifyUtils.parsePlaylistJSON(data);
                mTrackItemAdapter.updateTrackData(trackItems, trackItems);
                //GridView gridView = findViewById(R.id.gridview);
                //mPlaylistItemAdapter = new PlaylistItemAdapter(this, playlistItems);
                //gridView.setAdapter(mPlaylistItemAdapter);
                mTrackListRV.setVisibility(View.VISIBLE);
                Log.d("TrackActivity", "Load finished!");
                }
                }

        @Override
        public void onLoaderReset(android.content.Loader<String> loader) {

                }

        @Override
        public void onPTrackItemClick(String trackID) {
                Intent intent = new Intent(this, TrackActivity.class);
                intent.putExtra(SpotifyUtils.PlaylistItem.EXTRA_PLAYLIST_ITEM, trackID);
                this.startActivity(intent);
                }

}
