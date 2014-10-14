package com.example.jltolent.gridimageview.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.jltolent.gridimageview.R;
import com.example.jltolent.gridimageview.adapters.ImageResultsAdapter;
import com.example.jltolent.gridimageview.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private String searchUrl;
    private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent displayImage = new Intent(MainActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                displayImage.putExtra("result", result);
                startActivity(displayImage);
            }
        });
    }

    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();
        String searchUrl = generateSearchUrl(query);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray resultsJson = null;
                try {
                    resultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear(); // TODO clear ONLY when doing a new search, note for pagination
                    aImageResults.addAll(ImageResult.fromJsonArray(resultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onChangeSettings(MenuItem item) {
        Intent changeSettings = new Intent(this, SettingsActivity.class);

        // TODO make own helper func
        if (getIntent().hasExtra("size")) {
            changeSettings.putExtra("size", getIntent().getStringExtra("size"));
        }
        startActivity(changeSettings);
    }

    private void setupAdapter() {
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    private String generateSearchUrl(String query) {
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
                + query;
        if(getIntent().hasExtra("size")
                && !getIntent().getStringExtra("size").equals("any")) {
            searchUrl += "&imgsz=" + getIntent().getStringExtra("size");
        }
        return searchUrl;
    }
}
