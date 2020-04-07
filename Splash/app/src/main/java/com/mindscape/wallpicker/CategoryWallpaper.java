package com.mindscape.wallpicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.wallpicker.R;

/**
 * Created by Hakimi on 17/3/2020.
 */
public class CategoryWallpaper extends AppCompatActivity implements View.OnClickListener {
    private ImageView cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11;
    private int selected, controller;
    private String categoryPix = null;
    private TextView titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
        titleBar = findViewById(R.id.titleToolbar);
        titleBar.setText(R.string.categoryList);


        cat1 = findViewById(R.id.peopletCat);
        cat2 = findViewById(R.id.feelingCat);
        cat3 = findViewById(R.id.animalCat);
        cat4 = findViewById(R.id.computerCat);
        cat5 = findViewById(R.id.artisticCat);
        cat6 = findViewById(R.id.placesCat);
        cat7 = findViewById(R.id.backgroundCat);
        cat8 = findViewById(R.id.natureCat);
        cat9 = findViewById(R.id.buildingCat);
        cat10 = findViewById(R.id.musicCat);
        cat11 = findViewById(R.id.foodCat);

        cat1.setOnClickListener(this);
        cat2.setOnClickListener(this);
        cat3.setOnClickListener(this);
        cat4.setOnClickListener(this);
        cat5.setOnClickListener(this);
        cat6.setOnClickListener(this);
        cat7.setOnClickListener(this);
        cat8.setOnClickListener(this);
        cat9.setOnClickListener(this);
        cat10.setOnClickListener(this);
        cat11.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.peopletCat:
                selected = 0;
                categoryPix = "people";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.feelingCat:
                selected = 0;
                categoryPix = "feelings";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.animalCat:
                selected = 0;
                categoryPix = "animals";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.computerCat:
                selected = 0;
                categoryPix = "computer";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.artisticCat:
                selected = 0;
                categoryPix = "travel";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.placesCat:
                selected = 0;
                categoryPix = "places";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.backgroundCat:
                selected = 0;
                categoryPix = "backgrounds";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.natureCat:
                selected = 0;
                categoryPix = "nature";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.buildingCat:
                selected = 0;
                categoryPix = "buildings";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.musicCat:
                selected = 0;
                categoryPix = "music";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
            case R.id.foodCat:
                selected = 0;
                categoryPix = "food";
                controller = 1;
                categorySelected(selected, categoryPix, controller);
                break;
        }
    }

    public void categorySelected(int selected, String cat, int controller) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("theCategory", selected);
        intent.putExtra("pixCategory", cat);
        intent.putExtra("theController", controller);
        startActivity(intent);
        finish();
    }
}
