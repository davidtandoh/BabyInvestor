package com.example.babyinvestor.data.model.Portfolio;

public class PortfolioItem {

    private String ticker;
    private String percentage;
    private Float amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public PortfolioItem(String ticker, String percentage, Float amount,String name) {
        this.ticker = ticker;
        this.percentage = percentage;
        this.amount = amount;
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
