package com.mindscape.wallpicker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.wallpicker.R;

/**
 * Created by Hakimi on 21/3/2020.
 */
public class FavouritesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_activity);

        TextView titlebar = findViewById(R.id.titleToolbar);
        titlebar.setText(R.string.favouriteList);
    }
}
