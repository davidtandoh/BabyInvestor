package com.example.babyinvestor.data.model.Price;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Price {

    @SerializedName("regularMarketPrice")
    @Expose
    private RegularMarketPrice regMarketPrice;

    @SerializedName("regularMarketChangePercent")
    @Expose
    private RegularMarketChangePercent regMarketChangePercent;

    public RegularMarketPrice getRegMarketPrice() {
        return regMarketPrice;
    }

    public void setRegMarketPrice(RegularMarketPrice regMarketPrice) {
        this.regMarketPrice = regMarketPrice;
    }

    public RegularMarketChangePercent getRegMarketChangePercent() {
        return regMarketChangePercent;
    }

    public void setRegMarketChangePercent(RegularMarketChangePercent regMarketChangePercent) {
        this.regMarketChangePercent = regMarketChangePercent;
    }
}
