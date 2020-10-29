package com.example.babyinvestor.data.model.Summary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SummaryProfile {

    @SerializedName("sector")
    @Expose
    private String sector;

    @SerializedName("longBusinessSummary")
    @Expose
    private String longBusinessSummary;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("industry")
    @Expose
    private String industry;

    @SerializedName("website")
    @Expose
    private String website;


    @SerializedName("fullTimeEmployees")
    @Expose
    private String fullTimeEmployees;


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getLongBusinessSummary() {
        return longBusinessSummary;
    }

    public void setLongBusinessSummary(String longBusinessSummary) {
        this.longBusinessSummary = longBusinessSummary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFullTimeEmployees() {
        return fullTimeEmployees;
    }

    public void setFullTimeEmployees(String fullTimeEmployees) {
        this.fullTimeEmployees = fullTimeEmployees;
    }
}
