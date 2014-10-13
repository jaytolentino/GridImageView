package com.example.jltolent.gridimageview.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jay on 10/13/14.
 */
public class ImageResult {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult(JSONObject imageData) {
        try {
            this.fullUrl = imageData.getString("url");
            this.thumbUrl = imageData.getString("tbUrl");
            this.title = imageData.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
           for(int i = 0; i < array.length(); i++) {
               try {
                   results.add(new ImageResult(array.getJSONObject(i)));
               } catch (JSONException e ) {
                   e.printStackTrace();
               }
           }
        return  results;
    }
}
