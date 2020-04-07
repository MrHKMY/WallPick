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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//import com.android.wallpicker.R;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

/**
 * Created by Hakimi on 12/3/2020.
 */
public class DetailActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private ImageView imageView, userImageView, viewIcon, downloadICon, likeIcon;
    public HitsResult photo;
    public WallpaperResults photo2;
    private TextView views, downloads, likes, user;
    private ProgressBar progressBar;
    private int controller = 0;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        imageView = findViewById(R.id.photoImageView);
        userImageView = findViewById(R.id.userIV);
        views = findViewById(R.id.viewsTV);
        downloads = findViewById(R.id.downloadTV);
        likes = findViewById(R.id.likesTV);
        user = findViewById(R.id.userTV);
        progressBar = findViewById(R.id.progressBarImage);
        viewIcon = findViewById(R.id.viewIcon);
        downloadICon = findViewById(R.id.downloadIcon);
        likeIcon = findViewById(R.id.likeIcon);

        FloatingActionButton floatingActionButton1 = findViewById(R.id.fab1);
        FloatingActionButton floatingActionButton2 = findViewById(R.id.fab2);
        FloatingActionButton floatingActionButton3 = findViewById(R.id.fab3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (controller == 1) {
                    url = photo.getLargeImage();
                } else if (controller == 2) {
                    url = photo2.getUrl_image();
                }
                if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DetailActivity.this, "Required permission to access storage for downloading image", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                } else {
                    AlertDialog dialog = new SpotsDialog(DetailActivity.this);
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String fileName = UUID.randomUUID().toString() + ".jpg";
                    Picasso.get()
                            .load(url)
                            .into(new DownloadImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    fileName,
                                    "Image description"));
                    Toast.makeText(DetailActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (controller == 1) {
                    url = photo.getLargeImage();
                } else if (controller == 2) {
                    url = photo2.getUrl_image();
                }
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
                        Toast.makeText(DetailActivity.this, "New wallpaper has been set", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.get().load(url).into(target);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Coming soon...", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        photo = (HitsResult) bundle.getSerializable("images");
        photo2 = (WallpaperResults) bundle.getSerializable("abyss");
        if (bundle.getSerializable("images") != null) {
            populateActivity(photo);
            controller = 1;
        } else {
            populateAbyss(photo2);
            controller = 2;
        }
    }

    private void populateActivity(HitsResult photo) {
        Picasso.get()
                .load(photo.getLargeImage())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(DetailActivity.this, "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });

        if (photo.getUserImageURL() != "") {
            Picasso.get()
                    .load(photo.getUserImageURL())
                    .into(userImageView);
        }

        views.setText(photo.getViews());
        downloads.setText(photo.getDownload());
        likes.setText("Likes: " + photo.getLikes());
        user.setText("By: " + photo.getUser());
    }

    private void populateAbyss(WallpaperResults photo2) {
        progressBar.setVisibility(View.GONE);
        Picasso.get()
                .load(photo2.getUrl_thumb())
                .resize(1920, 1080)
                .into(imageView);

        views.setText("Width: " + photo2.getWidth());
        downloads.setText("Height: " + photo2.getHeight());
        viewIcon.setVisibility(View.INVISIBLE);
        downloadICon.setVisibility(View.INVISIBLE);
        likeIcon.setVisibility(View.INVISIBLE);
        likes.setVisibility(View.INVISIBLE);
        user.setVisibility(View.GONE);
    }
}
