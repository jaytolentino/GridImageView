package com.example.jltolent.gridimageview.models;

import java.io.Serializable;

/**
 * Created by jay on 10/15/14.
 */
public class SettingsData implements Serializable {
    private String mSize;
    private String mImageColor;
    private String mType;
    private String mSite;

    public SettingsData() {
        mSize = "any";
        mImageColor = "any";
        mType = "any";
        mSite = "";
    }

    public boolean hasSize() {
        return !mSize.equals("any");
    }

    public boolean hasColor() {
        return !mImageColor.equals("any");
    }

    public boolean hasType() {
        return !mType.equals("any");
    }

    public boolean hasSite() {
        return !mSite.equals("");
    }

    public String getSize() {
        return mSize;
    }

    public String getImageColor() {
        return mImageColor;
    }

    public String getType() {
        return mType;
    }

    public String getSite() {
        return mSite;
    }

    public void changeAttribute(String attribute, String value) {
        switch(attribute) {
            case "size":
                mSize = value;
                break;
            case "color":
                mImageColor = value;
                break;
            case "type":
                mType = value;
                break;
            case "site":
                mSite = value;
                break;
            default:
                break;
        }
    }
    public String getAttribute(String attribute) {
        switch(attribute) {
            case "size":
                return mSize;
            case "color":
                return mImageColor;
            case "type":
                return mType;
            case "site":
                return mSite;
            default:
                throw new NullPointerException();
        }
    }
}
