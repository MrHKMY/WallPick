package com.mindscape.wallpicker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hakimi on 9/3/2020.
 */
public interface SplashApiService {

    @GET("api")
    Call<JsonFetchPojo> getPhotos(@Query("key") String key, @Query("page") int page, @Query("image_type") String image_type, @Query("orientation") String orientation, @Query("category") String category, @Query("safesearch") String safe);

    @GET("api")
    Call<JsonFetchPojo> getSearch(@Query("key") String key, @Query("q") String query, @Query("page") int page, @Query("image_type") String image_type, @Query("orientation") String orientation, @Query("safesearch") String safe);

    @GET("api2.0/get.php")
    Call<AbyssPojo> getWallAbyss(@Query("auth") String authKey, @Query("method") String method, @Query("id") int id, @Query("page") int page);
}