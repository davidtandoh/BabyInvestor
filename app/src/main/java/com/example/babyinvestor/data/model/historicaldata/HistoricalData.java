package com.example.babyinvestor.data.model.historicaldata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoricalData {

    @SerializedName("prices")
    @Expose
    private List<StockItem> stockItems;

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockItem> stockItems) {
        this.stockItems = stockItems;
    }
}
