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
    private String color;
    private String type;

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
        toSearch.putExtra("color", color);
        toSearch.putExtra("type", type);
        startActivity(toSearch);
    }

    private void setupFields() {
        size = "any";
        color = "any";
        type = "any";
    }

    private void setupSpinners() {
        setupSpinner("size", R.id.spImageSize);
        setupSpinner("color", R.id.spColor);
        setupSpinner("type", R.id.spImageType);
        checkPreviousSettings();
    }

    private void checkPreviousSettings() {
        checkSettingsFor("size");
        checkSettingsFor("color");
        checkSettingsFor("type");
    }

    private void checkSettingsFor(String attribute) {
        if (getIntent().hasExtra(attribute)) {
            String previousSetting = getIntent().getStringExtra(attribute);
            if (!previousSetting.equals("any")) {
                switch(attribute) {
                    case "size":
                        adjustSizeSpinner(previousSetting);
                        break;
                    case "color":
                        adjustColorSpinner(previousSetting);
                        break;
                    case "type":
                        adjustTypeSpinner(previousSetting);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void adjustSizeSpinner(String sizeSetting) {
        Spinner spImageSize = (Spinner) findViewById(R.id.spImageSize);
        switch (sizeSetting) {
            case "small":
                spImageSize.setSelection(1);
                break;
            case "medium":
                spImageSize.setSelection(2);
                break;
            case "large":
                spImageSize.setSelection(3);
                break;
            case "xlarge":
                spImageSize.setSelection(4);
                break;
            default:
                spImageSize.setSelection(0);
                break;
        }
    }


    private void adjustColorSpinner(String colorSetting) {
        Spinner spColor = (Spinner) findViewById(R.id.spColor);
        switch (colorSetting) {
            case "black":
                spColor.setSelection(1);
                break;
            case "brown":
                spColor.setSelection(2);
                break;
            case "gray":
                spColor.setSelection(3);
                break;
            case "red":
                spColor.setSelection(4);
                break;
            case "orange":
                spColor.setSelection(5);
                break;
            case "yellow":
                spColor.setSelection(6);
                break;
            case "green":
                spColor.setSelection(7);
                break;
            case "blue":
                spColor.setSelection(8);
                break;
            case "purple":
                spColor.setSelection(9);
                break;
            case "white":
                spColor.setSelection(10);
                break;
            default:
                spColor.setSelection(0);
                break;
        }
    }

    private void adjustTypeSpinner(String typeSetting) {
        Spinner spImageType = (Spinner) findViewById(R.id.spImageType);
        switch (typeSetting) {
            case "faces":
                spImageType.setSelection(1);
                break;
            case "photo":
                spImageType.setSelection(2);
                break;
            case "clipart":
                spImageType.setSelection(3);
                break;
            case "lineart":
                spImageType.setSelection(4);
                break;
            default:
                spImageType.setSelection(0);
                break;
        }
    }

    private void setupSpinner(String attribute, int spinnerId) {
        final String finalAttribute = attribute;
        Spinner spinner = (Spinner) findViewById(spinnerId);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                changeAttribute(finalAttribute, parent.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void changeAttribute(String attribute, String value) {
        switch(attribute) {
            case "size":
                size = value;
                break;
            case "color":
                color = value;
                break;
            case "type":
                type = value;
                break;
            default:
                break;
        }
    }
}
