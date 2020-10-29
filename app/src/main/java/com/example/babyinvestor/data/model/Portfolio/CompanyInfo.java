package com.example.babyinvestor.data.model.Portfolio;

import java.io.Serializable;

public class CompanyInfo implements Serializable {

    private String name;
    private Float weight;

    public CompanyInfo(String name, Float weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
