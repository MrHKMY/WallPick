package com.mindscape.wallpicker;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hakimi on 9/3/2020.
 */
public class HitsResult implements Serializable {

    @SerializedName("largeImageURL")
    private String largeImage;
    @SerializedName("previewURL")
    private String preview;
    @SerializedName("webformatURL")
    private String webformat;
    @SerializedName("id")
    private String id;
    @SerializedName("pageURL")
    private String pageURL;
    @SerializedName("views")
    private String views;
    @SerializedName("downloads")
    private String download;
    @SerializedName("likes")
    private String likes;
    @SerializedName("user")
    private String user;
    @SerializedName("userImageURL")
    private String userImageURL;

    public HitsResult(String largeImage, String preview, String id, String pageURL, String views, String download, String likes, String user, String userImageURL, String webformatUrl) {
        this.largeImage = largeImage;
        this.preview = preview;
        this.id = id;
        this.pageURL = pageURL;
        this.views = views;
        this.download = download;
        this.likes = likes;
        this.user = user;
        this.userImageURL = userImageURL;
        this.webformat = webformatUrl;
    }

    public String getId() {
        return id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getViews() {
        return views;
    }

    public String getDownload() {
        return download;
    }

    public String getLikes() {
        return likes;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public String getPreview() {
        return preview;
    }

    public String getWebformat() {
        return webformat;
    }

    public String getLargeImage() {
        return largeImage;
    }
}