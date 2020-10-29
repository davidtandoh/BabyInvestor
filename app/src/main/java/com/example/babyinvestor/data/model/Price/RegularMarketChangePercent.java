package com.example.babyinvestor.data.model.Price;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegularMarketChangePercent {

    @SerializedName("raw")
    @Expose
    private String raw;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
