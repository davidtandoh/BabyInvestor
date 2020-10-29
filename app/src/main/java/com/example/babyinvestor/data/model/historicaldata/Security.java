package com.example.babyinvestor.data.model.historicaldata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Security {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ticker")
    @Expose
    private String ticker;

    @SerializedName("currency")
    @Expose
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
