package com.cs492.finalproject.spotifyapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;


import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback, LoaderManager.LoaderCallbacks<String>,
        NavigationView.OnNavigationItemSelectedListener

{
    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "17fbced129bb41e7a78b0b58312905e1";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "cs492final://callback";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    private Player mPlayer;
    private CategoryItemAdapter mCategoryItemAdapter;
    private String mToken;
    private RecyclerView mTrackItemsRV;
    private TrackItemAdapter mTrackItemAdapter;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mFavoriteItemsRV;
    private ActionBarDrawerToggle mDrawerToggle;

    private static final String TAG = MainActivity.class.getSimpleName();

    private SQLiteDatabase mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

//        mFavoriteAdapter = new FavoriteAdapter(this, this);
//        mFavoriteItemsRV.setAdapter(mFavoriteAdapter);
//        mFavoriteItemsRV.setLayoutManager(new LinearLayoutManager(this));
//        mFavoriteItemsRV.setHasFixedSize(true);

        FavoritesDBHelper dbHelper = new FavoritesDBHelper(this);
        mDB = dbHelper.getReadableDatabase();
        loadDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



                if (mDrawerToggle.onOptionsItemSelected(item)) {

                    return true;
                }
                return super.onOptionsItemSelected(item);

    }
    public void loadCategories(String token, boolean initialLoad) {
        Bundle loaderArgs = new Bundle();
        loaderArgs.putString("token", token);
        LoaderManager loaderManager = getLoaderManager();
        if (initialLoad) {
            loaderManager.initLoader(0, loaderArgs, this);
        } else {
            loaderManager.restartLoader(0, loaderArgs, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                mToken = response.getAccessToken();
                Config playerConfig = new Config(this, mToken, CLIENT_ID);
                loadCategories(mToken, true);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    private void loadDrawer() {
        Cursor cursor = mDB.query(
                FavoritesContract.FavoriteTracks.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoritesContract.FavoriteTracks.COLUMN_NAME + " DESC"
        );
//        while (cursor.moveToNext()) {
//            String favorite = cursor.getString(
//                    cursor.getColumnIndex(FavoritesContract.FavoriteTracks.COLUMN_NAME)
//            );
//           cursor.close();
//
//        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");

        mPlayer.playUri(null, "spotify:track:43ZyHQITOjhciSUUNPVRHc", 0, 0);
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MainActivity", "Login failed");
    }


    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }


    /*
    * This for the http request stuff
    */

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String token = null;
        if (args != null) {
            token = args.getString("token");
        }
        return new SpotifyCategoryTask(this, token);
    }


    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        if (data != null) {
            ArrayList<SpotifyUtils.CategoryItem> categoryItems = SpotifyUtils.parseCategoryJSON(data);
            GridView gridView = findViewById(R.id.gridview);
            mCategoryItemAdapter = new CategoryItemAdapter(this, categoryItems, mToken);
            gridView.setAdapter(mCategoryItemAdapter);
            Log.d("MainActivity", "Load finished!");
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

//    @Override
//    public void onFavoriteItemClick(FavoriteAdapter.FavoriteItem favoriteItem) {
//        Log.d(TAG, "loading favorite: " + favoriteItem.favorite);
//        String favorite = favoriteItem.favorite;
////        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
////        editor.putString(getString(R.string.pref_location_key), location);
////        editor.apply();
////        Log.d(TAG, "Preference updated");
//    }
}