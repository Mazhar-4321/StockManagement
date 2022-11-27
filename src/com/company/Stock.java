package com.company;

public class Stock {
    private String shareName;
    private Integer noOfShares;
    private Integer sharePrice;

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public Integer getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(Integer noOfShares) {
        this.noOfShares = noOfShares;
    }

    public Integer getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Integer sharePrice) {
        this.sharePrice = sharePrice;
    }
}
