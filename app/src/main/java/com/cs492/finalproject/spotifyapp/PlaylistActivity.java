package com.cs492.finalproject.spotifyapp;

/**
 * Created by tasniakabir on 3/18/18.
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

public class PlaylistActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>, PlaylistItemAdapter.OnPlaylistItemClickListener {
    private RecyclerView mPlaylistListRV;
    private PlaylistItemAdapter mPlaylistItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        mPlaylistListRV = (RecyclerView)findViewById(R.id.rv_playlist);

        mPlaylistListRV.setLayoutManager(new LinearLayoutManager(this));
        mPlaylistListRV.setHasFixedSize(true);

        mPlaylistItemAdapter = new PlaylistItemAdapter(this);
        mPlaylistListRV.setAdapter(mPlaylistItemAdapter);

        mPlaylistListRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String categoryID = intent.getStringExtra(SpotifyUtils.CategoryItem.EXTRA_CATEGORY_ITEM);
        loadPlaylists(token, categoryID, true);
    }

    public void loadPlaylists(String token, String categoryID, boolean initialLoad) {
        Bundle loaderArgs = new Bundle();
        loaderArgs.putString("token", token);
        loaderArgs.putString("categoryID", categoryID);
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
        String categoryID = null;
        if (args != null) {
            token = args.getString("token");
            categoryID  = args.getString("categoryID");
        }
        return new SpotifyPlaylistTask(this, token, categoryID);
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        if (data != null) {
            ArrayList<SpotifyUtils.PlaylistItem> playlistItems = SpotifyUtils.parsePlaylistJSON(data);
            mPlaylistItemAdapter.updatePlaylistData(playlistItems, playlistItems);
            //GridView gridView = findViewById(R.id.gridview);
            //mPlaylistItemAdapter = new PlaylistItemAdapter(this, playlistItems);
            //gridView.setAdapter(mPlaylistItemAdapter);
            mPlaylistListRV.setVisibility(View.VISIBLE);
            Log.d("PlaylistActivity", "Load finished!");
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {

    }

    @Override
    public void onPlaylistItemClick(String playlistID) {
        Intent intent = new Intent(this, PlaylistActivity.class);
        intent.putExtra(SpotifyUtils.CategoryItem.EXTRA_CATEGORY_ITEM, playlistID);
        this.startActivity(intent);
    }
}
