package com.example.jltolent.gridimageview.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jltolent.gridimageview.R;

public class SettingsActivity extends Activity {
    private String size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupFields();
        setupSpinners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    public void onSaveSettings(View view) {
        Intent toSearch = new Intent(this, MainActivity.class);
        toSearch.putExtra("size", size);
        startActivity(toSearch);
    }

    private void setupFields() {
        size = "any";
    }

    private void setupSpinners() {
        Spinner spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                size = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        checkPreviousSettings();
    }

    private void checkPreviousSettings() {
        if (getIntent().hasExtra("size")) {
            String sizeSetting = getIntent().getStringExtra("size");
            if (!sizeSetting.equals("any")) {
                Spinner spImageSize = (Spinner) findViewById(R.id.spImageSize);
                if (sizeSetting.equals("small")) {
                    spImageSize.setSelection(1);
                }
                else if (sizeSetting.equals("medium")) {
                    spImageSize.setSelection(2);
                }
                else if (sizeSetting.equals("large")) {
                    spImageSize.setSelection(3);
                }
                else if (sizeSetting.equals("xlarge")) {
                    spImageSize.setSelection(4);
                }
            }
        }
    }

    // TODO remove this test toast
    private void toast() {
        Toast.makeText(this, size, Toast.LENGTH_SHORT).show();
    }
}
