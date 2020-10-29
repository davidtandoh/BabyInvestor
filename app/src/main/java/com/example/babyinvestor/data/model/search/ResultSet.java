package com.example.babyinvestor.data.model.search;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultSet {

    @SerializedName("Result")
    @Expose
    private List<ResultItem> results;

    public List<ResultItem> getResults() {
        return results;
    }

    public void setResults(List<ResultItem> results) {
        this.results = results;
    }
}
