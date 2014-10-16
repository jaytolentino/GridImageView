package com.example.jltolent.gridimageview.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jltolent.gridimageview.R;
import com.example.jltolent.gridimageview.activities.MainActivity;
import com.example.jltolent.gridimageview.models.SettingsData;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class EditSettingsDialog extends DialogFragment {
    private View currentView;
    private SettingsData mSettings;
    private OnDataPass dataPasser;

    public EditSettingsDialog() {
        // Required empty public constructor
    }

    public static EditSettingsDialog newInstance(String title) {
        EditSettingsDialog frag = new EditSettingsDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_edit_settings_dialog, container);
        String title = getArguments().getString("title");

        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final View btnSave = currentView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPasser.onDataPass(mSettings);
                getDialog().dismiss();
            }
        });

        mSettings = (SettingsData) getArguments().getSerializable("settingsData");
        setupSpinners();

        return currentView;
    }

    private void setupSpinners() {
        setupSpinner("size", R.id.spImageSize);
        setupSpinner("color", R.id.spColor);
        setupSpinner("type", R.id.spImageType);
        checkPreviousSettings();
    }

    private void setupSpinner(String attribute, int spinnerId) {
        final String finalAttribute = attribute;
        Spinner spinner = (Spinner) currentView.findViewById(spinnerId);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                mSettings.changeAttribute(finalAttribute, parent.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public interface OnDataPass {
        public void onDataPass(SettingsData data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataPasser = (OnDataPass) activity;
    }

    private void checkPreviousSettings() {
        checkSettingsFor("size");
        checkSettingsFor("color");
        checkSettingsFor("type");
        checkSettingsFor("site");
    }

    private void checkSettingsFor(String attribute) {
        String previousSetting = mSettings.getAttribute(attribute);
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
                case "site":
                    adjustSiteText(previousSetting);
                    break;
                default:
                    break;
            }
        }
    }

    private void adjustSizeSpinner(String sizeSetting) {
        Spinner spImageSize = (Spinner) currentView.findViewById(R.id.spImageSize);
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
        Spinner spColor = (Spinner) currentView.findViewById(R.id.spColor);
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

    private void adjustSiteText(String siteSetting) {
        EditText etSite = (EditText) currentView.findViewById(R.id.etSite);
        etSite.setText(siteSetting);
    }

    private void adjustTypeSpinner(String typeSetting) {
        Spinner spImageType = (Spinner) currentView.findViewById(R.id.spImageType);
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


}
