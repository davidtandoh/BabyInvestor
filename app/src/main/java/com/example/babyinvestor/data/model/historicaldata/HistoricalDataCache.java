package com.example.babyinvestor.data.model.historicaldata;

import java.util.ArrayList;
import java.util.List;

public class HistoricalDataCache {

    private static HistoricalDataCache sCache;
    private static List<StockItem> stockItems;

    private HistoricalDataCache() {}

    public static HistoricalDataCache getHistoricalDataCache() {
        if(sCache == null) {
            sCache = new HistoricalDataCache();
            stockItems = new ArrayList<>();
        }
        return sCache;
    }

    public List<StockItem> getHistoricalValues() {
        return stockItems;
    }

    public void setHistoricalValues(List<StockItem> list) {
        stockItems.clear();
        stockItems.addAll(list);
    }

    public StockItem getStockValues(String symbol) {
        if(symbol != null) {
            for(StockItem stock : stockItems) {
                if(stock.getSymbol().equals(symbol))
                    return stock;
            }
        }
        return null;
    }
}
