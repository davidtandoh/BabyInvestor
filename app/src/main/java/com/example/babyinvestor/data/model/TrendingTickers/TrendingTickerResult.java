package com.example.babyinvestor.data.model.TrendingTickers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingTickerResult{
    @SerializedName("result")
    @Expose
    private List<Quotes> results;

    public List<Quotes> getResults() {
        return results;
    }

    public void setResults(List<Quotes> results) {
        this.results = results;
    }
}