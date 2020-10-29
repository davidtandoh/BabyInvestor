package com.example.babyinvestor.data.model.TrendingTickers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Quotes{
    @SerializedName("quotes")
    @Expose
    private List<QuoteItems> quotes;

    public List<QuoteItems> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteItems> quotes) {
        this.quotes = quotes;
    }
}