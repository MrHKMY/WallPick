package com.mindscape.wallpicker;

import android.provider.BaseColumns;

/**
 * Created by Hakimi on 2/5/2020.
 */
public class FavouriteContract {

    private FavouriteContract() {

    }

    public static final class FavouriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "myTable";
        public static final String COLUMN_NAME = "name";
    }
}
