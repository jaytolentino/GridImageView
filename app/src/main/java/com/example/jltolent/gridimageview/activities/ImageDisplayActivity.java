package com.example.jltolent.gridimageview.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.widget.ShareActionProvider;

import com.example.jltolent.gridimageview.R;
import com.example.jltolent.gridimageview.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends ActionBarActivity {
    private ShareActionProvider miShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miShareAction = new ShareActionProvider(this);
        setContentView(R.layout.activity_image_display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
        MenuItem item = menu.findItem(R.id.actionShare);
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading...");
        progress.setMessage("Please wait.");
        progress.show();

        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        Picasso.with(this)
                .load(result.fullUrl)
                .placeholder(R.drawable.ic_launcher)
                .resize(600, 600)
                .centerInside()
                .into(ivImageResult, new Callback() {
                    @Override
                    public void onSuccess() {
                        setupShareIntent();
                        progress.dismiss();
                    }

                    @Override
                    public void onError() {

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

    private void setupShareIntent() {
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        Uri bmpUri = getLocalBitmapUri(ivImageResult);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        miShareAction.setShareIntent(shareIntent);
    }

    private Uri getLocalBitmapUri(ImageView imageView) {
        Drawable mDrawable = imageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                mBitmap, "Description", null);

        Uri uri = Uri.parse(path);
        return uri;
    }
}
