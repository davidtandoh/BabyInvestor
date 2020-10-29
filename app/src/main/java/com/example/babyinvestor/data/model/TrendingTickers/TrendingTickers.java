package com.example.babyinvestor.data.model.TrendingTickers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingTickers {

    @SerializedName("finance")
    @Expose
    private TrendingTickerResult trendingTickerResults;

    public TrendingTickerResult getTrendingTickerResults() {
        return trendingTickerResults;
    }

    public void setTrendingTickerResults(TrendingTickerResult trendingTickerResults) {
        this.trendingTickerResults = trendingTickerResults;
    }
}
