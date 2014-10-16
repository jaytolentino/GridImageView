package com.example.jltolent.gridimageview.adapters;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jltolent.gridimageview.R;
import com.example.jltolent.gridimageview.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jay on 10/13/14.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(imageInfo.thumbUrl).placeholder(R.drawable.ic_launcher).into(ivImage);

        return convertView;
    }

}
