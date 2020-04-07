package com.mindscape.wallpicker;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hakimi on 15/3/2020.
 */
public class WallpaperResults implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("url_image")
    private String url_image;

    @SerializedName("url_thumb")
    private String url_thumb;

    @SerializedName("url_page")
    private String url_page;

    @SerializedName("width")
    private String width;

    @SerializedName("height")
    private String height;

    public WallpaperResults(int id, String url_image, String url_thumb, String url_page, String width, String height, int wallpaperCount) {
        this.id = id;
        this.url_image = url_image;
        this.url_thumb = url_thumb;
        this.url_page = url_page;
        this.width = width;
        this.height = height;
        this.wallpaperCount = wallpaperCount;
    }

    private int wallpaperCount = 100;

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public String getUrl_image() {
        return url_image;
    }

    public String getUrl_thumb() {
        return url_thumb;
    }

    public String getUrl_page() {
        return url_page;
    }

    public int getWallpaperCount() {
        return wallpaperCount;
    }
}
