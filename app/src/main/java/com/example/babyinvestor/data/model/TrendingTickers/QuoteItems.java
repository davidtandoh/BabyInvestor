package com.example.babyinvestor.data.model.TrendingTickers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoteItems{
    @SerializedName("symbol")
    @Expose
    public String ticker_symbol;

    @SerializedName("longName")
    @Expose
    public String company_name;

    @SerializedName("regularMarketPrice")
    @Expose
    public double last_price;

    @SerializedName("regularMarketChange")
    @Expose
    public String market_change;

    @SerializedName("regularMarketChangePercent")
    @Expose
    public String percent_change;

    public String getTicker_symbol() {
        return ticker_symbol;
    }

    public void setTicker_symbol(String ticker_symbol) {
        this.ticker_symbol = ticker_symbol;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public double getLast_price() {
        return last_price;
    }

    public void setLast_price(Double last_price) {
        this.last_price = last_price;
    }

    public String getMarket_change() {
        return market_change;
    }

    public void setMarket_change(String market_change) {
        this.market_change = market_change;
    }

    public String getPercent_change() {
        return percent_change;
    }

    public void setPercent_change(String percent_change) {
        this.percent_change = percent_change;
    }
}