package com.mindscape.wallpicker;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Hakimi on 13/3/2020.
 */
class DownloadImageHelper implements Target {

    private WeakReference<AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeaReference;
    private String name, description;

    public DownloadImageHelper(Context baseContext, AlertDialog alertDialog, ContentResolver contentResolver, String name, String description) {
        this.alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeaReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.description = description;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver r = contentResolverWeaReference.get();
        AlertDialog dialog = alertDialogWeakReference.get();

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/WallPicker");
        if (!path.exists()) {
            path.mkdir();
        }
        if (r != null) {
            MediaStore.Images.Media.insertImage(r, bitmap, name, description);
        }
        dialog.dismiss();
        //Toast.makeText(this, "Download complete", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
