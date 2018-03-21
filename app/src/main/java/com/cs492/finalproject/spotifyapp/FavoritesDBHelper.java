package com.cs492.finalproject.spotifyapp;

import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tasniakabir on 3/21/18.
 */

public class FavoritesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteTracks.db";
    private static int DATABASE_VERSION = 1;

        public FavoritesDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String SQL_CREATE_FAVORITES_TABLE =
                    "CREATE TABLE " + FavoritesContract.FavoriteTracks.TABLE_NAME + "(" +
                            FavoritesContract.FavoriteTracks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            FavoritesContract.FavoriteTracks.COLUMN_NAME + " TEXT NOT NULL, " +
                            ");";
            db.execSQL(SQL_CREATE_FAVORITES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoriteTracks.TABLE_NAME + ";");
            onCreate(db);
        }
}
