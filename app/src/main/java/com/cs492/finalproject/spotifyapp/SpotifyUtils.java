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

    public static String buildCategoryUrl() {
        return Uri.parse(SPOTIFY_BASE_URL + "/v1/browse/categories").buildUpon()
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
}
