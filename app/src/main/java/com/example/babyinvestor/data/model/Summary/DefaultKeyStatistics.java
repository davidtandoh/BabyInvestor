package com.example.babyinvestor.data.model.Summary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultKeyStatistics {

    public SummaryProfile getSummaryProfile() {
        return summaryProfile;
    }

    public void setSummaryProfile(SummaryProfile summaryProfile) {
        this.summaryProfile = summaryProfile;
    }

    @SerializedName("summaryProfile")
    @Expose
    private SummaryProfile summaryProfile;
}
