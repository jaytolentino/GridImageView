package com.example.jltolent.gridimageview.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.jltolent.gridimageview.R;
import com.example.jltolent.gridimageview.adapters.EndlessScrollListener;
import com.example.jltolent.gridimageview.adapters.ImageResultsAdapter;
import com.example.jltolent.gridimageview.fragments.EditSettingsDialog;
import com.example.jltolent.gridimageview.models.ImageResult;
import com.example.jltolent.gridimageview.models.SettingsData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.example.jltolent.gridimageview.net.NetworkHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements EditSettingsDialog.OnDataPass {
    private String query;
    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private final int RESULTS_COUNT = 8;
    private String searchUrl;
    private SettingsData mSettings;

    @Override
    public void onDataPass(SettingsData data) {
        Toast.makeText(this, data.getImageColor(), Toast.LENGTH_SHORT).show();
        mSettings = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = "";
        mSettings = new SettingsData();
        setupViews();
        setupAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                onImageSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


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
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent displayImage = new Intent(MainActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                displayImage.putExtra("result", result);
                startActivity(displayImage);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String searchForMoreUrl = searchUrl + "&start=" + imageResults.size();
                Log.i("INFO", "Search for More Url: " + searchForMoreUrl);

                ConnectivityManager manager = (ConnectivityManager)
                        MainActivity.this.getSystemService(CONNECTIVITY_SERVICE);

                if (NetworkHandler.networkIsAvailable(manager)) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(searchForMoreUrl, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            JSONArray resultsJson = null;
                            try {
                                resultsJson = response.getJSONObject("responseData").getJSONArray("results");
                                aImageResults.addAll(ImageResult.fromJsonArray(resultsJson));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("No Network Available");
                    builder.setMessage("Please check your connectivity.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    });
                    builder.show();
                }
            }
        });
    }

    public void onImageSearch() {
        String searchUrl = generateSearchUrl(query);

        if (NetworkHandler.networkIsAvailable(
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(searchUrl, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray resultsJson = null;
                    try {
                        resultsJson = response.getJSONObject("responseData").getJSONArray("results");
                        imageResults.clear();
                        aImageResults.addAll(ImageResult.fromJsonArray(resultsJson));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("No Network Available");
            builder.setMessage("Please check your connectivity.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });
            builder.show();
        }
    }

    public void onChangeSettings(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("settingsData", mSettings);
        bundle.putString("title", "Edit Settings");

        FragmentManager fm = getSupportFragmentManager();
        EditSettingsDialog editSettingsDialog = EditSettingsDialog.newInstance("Edit Settings");
        editSettingsDialog.setArguments(bundle);

        editSettingsDialog.show(fm, "fragment_edit_settings_dialog");
    }

    private void setupAdapter() {
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    private String generateSearchUrl(String query) {
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
                + query;
        if(mSettings.hasSize()) {
            searchUrl += "&imgsz=" + mSettings.getSize();
        }
        if(mSettings.hasColor()) {
            searchUrl += "&imgcolor=" + mSettings.getImageColor();
        }
        if(mSettings.hasType()) {
            searchUrl += "&imgtype=" + mSettings.getType();
        }
        if(mSettings.hasSite()) {
            searchUrl += "&as_sitesearch=" + mSettings.getSite();
        }
        searchUrl += "&rsz=" + RESULTS_COUNT;
        this.searchUrl = searchUrl;
        Log.i("INFO", "Search URL: " + searchUrl);
        return searchUrl;
    }
}
