package com.example.babyinvestor.data.model.historicaldata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockItem {

    @SerializedName("security")
    @Expose
    private Security security;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    @SerializedName("open")
    @Expose
    private double open_price;

    @SerializedName("low")
    @Expose
    private double low_price;

    @SerializedName("high")
    @Expose
    private double high_price;

    @SerializedName("close")
    @Expose
    private double close_price;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("date")
    @Expose
    private String date;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;


    public double getLow_price() {
        return low_price;
    }

    public void setLow_price(double low_price) {
        this.low_price = low_price;
    }

    public double getHigh_price() {
        return high_price;
    }

    public void setHigh_price(double high_price) {
        this.high_price = high_price;
    }

    public double getClose_price() {
        return close_price;
    }

    public void setClose_price(double close_price) {
        this.close_price = close_price;
    }

    public double getOpen_price() {
        return open_price;
    }

    public void setOpen_price(double open_price) {
        this.open_price = open_price;
    }



}
