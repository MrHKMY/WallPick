package com.mindscape.wallpicker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hakimi on 9/3/2020.
 */
public class JsonFetchPojo implements Serializable {

    @SerializedName("total")
    private int total;
    @SerializedName("totalHits")
    private int totalHits;
    @SerializedName("hits")
    @Expose
    private ArrayList<HitsResult> hitsResults;

    public JsonFetchPojo(int total, int totalHits, ArrayList<HitsResult> hitsResults) {
        this.total = total;
        this.totalHits = totalHits;
        this.hitsResults = hitsResults;
    }

    public int getTotalHits() {
        return totalHits;
    }


    public ArrayList<HitsResult> getHitsResults() {
        return hitsResults;
    }
}
