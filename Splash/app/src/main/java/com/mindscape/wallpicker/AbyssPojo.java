package com.mindscape.wallpicker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hakimi on 15/3/2020.
 */
class AbyssPojo implements Serializable {

    @SerializedName("success")
    private String status;

    @SerializedName("wallpapers")
    @Expose
    private ArrayList<WallpaperResults> wallpaperResults;


    public AbyssPojo(String status, ArrayList<WallpaperResults> wallpaperResults) {
        this.status = status;
        this.wallpaperResults = wallpaperResults;

    }

    public String getStatus() {
        return status;
    }

    public ArrayList<WallpaperResults> getWallpaperResults() {
        return wallpaperResults;
    }
}
