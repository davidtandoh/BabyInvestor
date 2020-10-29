package com.example.babyinvestor.data.model.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCik() {
        return cik;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getLei() {
        return lei;
    }

    public void setLei(String lei) {
        this.lei = lei;
    }

    @SerializedName("id")
    @Expose
    private String company_id;

    @SerializedName("ticker")
    @Expose
    private String ticker;

    @SerializedName("name")
    @Expose
    private String company_name;

    @SerializedName("cik")
    @Expose
    private String cik;

    @SerializedName("lei")
    @Expose
    private String lei;
}
