package com.gamehunter.lukasz.gamehunter.model;

/**
 * Created by Łukasz on 2017-01-13.
 */

public class Deal {
    public Deal(String name, String price, String dealRating, String dealID) {
        this.name = name;
        this.price = price;
        this.dealRating = dealRating;
        this.dealID = dealID;
    }

    private String name;
    private String price;
    private String dealRating;
    private String dealID;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDealRating() {
        return dealRating;
    }

    public void setDealRating(String dealRating) {
        this.dealRating = dealRating;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
