package com.cs492.finalproject.spotifyapp;

import android.net.ParseException;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tasniakabir on 3/18/18.
 */

public class SpotifyUtils {
    private final static String SPOTIFY_BASE_URL = "https://api.spotify.com";
    private final static String CATEGORY_URL = "/v1/browse/categories/";
    private final static String PLAYLIST_URL = "/playlists";
    private final static String TRACK_URL = "/tracks";

    public static String buildCategoryUrl() {
        return Uri.parse(SPOTIFY_BASE_URL + CATEGORY_URL).buildUpon()
                .build()
                .toString();
    }

    public static class CategoryItem implements Serializable {
        public static final String EXTRA_CATEGORY_ITEM = "com.cs492.finalproject.spotifyapp";
        public String name;
        public String ID;
        public String imageURL;
    }

    public static ArrayList<CategoryItem> parseCategoryJSON(String categoryJSON) {
        try {
            JSONObject categoryObj = new JSONObject(categoryJSON);
            JSONArray categoryList = categoryObj.getJSONObject("categories").getJSONArray("items");

            ArrayList<CategoryItem> categoryItemsList = new ArrayList<CategoryItem>();
            for (int i = 0; i < categoryList.length(); i++) {
                CategoryItem categoryItem = new CategoryItem();
                JSONObject categoryListElem = categoryList.getJSONObject(i);

                categoryItem.name = categoryListElem.getString("name");
                categoryItem.ID = categoryListElem.getString("id");
                categoryItem.imageURL = categoryListElem.getJSONArray("icons").getJSONObject(0).getString("url");
                categoryItemsList.add(categoryItem);
            }
            return categoryItemsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildPlaylistsUrl(String categoryID) {
        return Uri.parse(SPOTIFY_BASE_URL + CATEGORY_URL +  categoryID + PLAYLIST_URL  ).buildUpon()
                .build()
                .toString();
    }

    public static class PlaylistItem implements  Serializable {
      //public static final String EXTRA_PLAYLIST_ITEM = "com.cs492.finalproject.spotifyapp";
      public String name;
      public String ID;
      public String imageURL;
      public String tracksURL;
    }

    public static ArrayList<PlaylistItem> parsePlaylistJSON(String playlistJSON) {
        try {
            JSONObject playlistObj = new JSONObject(playlistJSON);
            JSONArray playlistList = playlistObj.getJSONObject("playlists").getJSONArray("items");

            ArrayList<PlaylistItem> playlistItemsList = new ArrayList<>();
            for (int i = 0; i < playlistList.length(); i++) {
                PlaylistItem playlistItem = new PlaylistItem();
                JSONObject playlistListElem = playlistList.getJSONObject(i);

                playlistItem.name = playlistListElem.getString("name");
                playlistItem.ID = playlistListElem.getString("id");
                playlistItem.imageURL = playlistListElem.getJSONArray("images").getJSONObject(0).getString("url");
                playlistItem.tracksURL = playlistListElem.getJSONObject("tracks").getString("href");
                playlistItemsList.add(playlistItem);
            }
            return playlistItemsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildTrackUrl(String categoryID) {
        return Uri.parse(SPOTIFY_BASE_URL + CATEGORY_URL +  categoryID + PLAYLIST_URL).buildUpon()
                .build()
                .toString();
    }

    public static class TrackItem implements Serializable {
        public static final String EXTRA_TRACK_ITEM = "com.cs492.finalproject.spotifyapp";
        public String name;
        public String ID;
        public String imageURL;
    }

    public static ArrayList<TrackItem> parseTrackJSON(String trackJSON) {
        try {
            JSONObject trackObj = new JSONObject(trackJSON);
            JSONArray trackList = trackObj.getJSONObject("track").getJSONArray("items");

            ArrayList<TrackItem> trackItemsList = new ArrayList<TrackItem>();
            for (int i = 0; i < trackList.length(); i++) {
                TrackItem trackItem = new TrackItem();
                JSONObject trackListElem = trackList.getJSONObject(i);

                trackItem.name = trackListElem.getString("name");
                trackItem.ID = trackListElem.getString("id");
                trackItem.imageURL = trackListElem.getJSONArray("icons").getJSONObject(0).getString("url");
                trackItemsList.add(trackItem);
            }
            return trackItemsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



}
