package com.mindscape.wallpicker;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

/**
 * Created by Hakimi on 5/5/2020.
 */
public class FavouriteDetailActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_detail);

        imageView = findViewById(R.id.favouriteImageViewDetail);
        progressBar = findViewById(R.id.progressBarFavouriteDetail);

        FloatingActionButton floatingActionButton1 = findViewById(R.id.fabFavDetail1);
        FloatingActionButton floatingActionButton2 = findViewById(R.id.fabFavDetail2);
        FloatingActionButton floatingActionButton3 = findViewById(R.id.fabFavDetail3);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String linkFromDatabase = bundle.getString("theLink");

        Picasso.get()
                .load(linkFromDatabase)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                
                if (ActivityCompat.checkSelfPermission(FavouriteDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(FavouriteDetailActivity.this, "Required permission to access storage for downloading image", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                } else {
                    AlertDialog dialog = new SpotsDialog(FavouriteDetailActivity.this);
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String fileName = UUID.randomUUID().toString() + ".jpg";
                    Picasso.get()
                            .load(linkFromDatabase)
                            .into(new DownloadImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    fileName,
                                    "Image description"));
                    Toast.makeText(FavouriteDetailActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            wallpaperManager.setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            //Log.e(TAG, "IOException->" + e.getMessage());
                        }
                        Toast.makeText(FavouriteDetailActivity.this, "New wallpaper has been set", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.get().load(linkFromDatabase).into(target);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FavouriteDetailActivity.this, "Removed from favourites.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
