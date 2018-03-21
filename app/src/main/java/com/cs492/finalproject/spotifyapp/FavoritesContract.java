package com.cs492.finalproject.spotifyapp;

import android.provider.BaseColumns;

/**
 * Created by tasniakabir on 3/21/18.
 */

public class FavoritesContract {
    private FavoritesContract() {}
    public static class FavoriteTracks implements BaseColumns {
        public static final String TABLE_NAME = "favoriteTracks";
        public static final String COLUMN_NAME = "trackName";
    }
}

