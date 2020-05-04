package com.mindscape.wallpicker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hakimi on 21/3/2020.
 */
public class FavouritesActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private FavouriteViewAdapter mAdapter;
    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_activity);

        TextView titlebar = findViewById(R.id.titleToolbar);
        titlebar.setText(R.string.favouriteList);

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.favouriteRecyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        mAdapter = new FavouriteViewAdapter(this, loadFavourites());
        recyclerView.setAdapter(mAdapter);

    }

    private Cursor loadFavourites() {
        return mDatabase.query(
                FavouriteContract.FavouriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


}
