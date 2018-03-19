package com.cs492.finalproject.spotifyapp;

/**
 * Created by tasniakabir on 3/18/18.
 */

import android.graphics.Bitmap;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final OkHttpClient mHTTPClient = new OkHttpClient();
   // public static final String O_AUTH_AUTHENTICATION = "application/json";

    public static String doHTTPGet(String url, String token) throws IOException {
        //String token = "Bearer BQBxWhCLyjJpjr-kaC6RM-KWr21Ng10E0d9hfoEAvAj6R3UwTLdBNIIjgFGO5JqAg96RJ3VJw5AZEOWM2i7nEU1OJrzqpBDEMGeh_JSnFDjlCWWfuNFLNuh9kd9CzTLHgl31C4VV9DapfpVBSrX-qn5AovHPwLpBeyV4slHu0uc4b27r3f6vT3r029WzmUWm8ALT3029Hz1pAb_9lJqOa0Xs57vfqt1ZXgc9CsgLYIvW_A5icKltVYsIcyJVhEk6QZ2Yxw73jtj3";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();
        Response response = mHTTPClient.newCall(request).execute();

        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }

}
