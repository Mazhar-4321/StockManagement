package com.company;

public class CompanyShares {
    private String symbol;
    private int sharePrice;
    private int sharesAvailableToTrade;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(int sharePrice) {
        this.sharePrice = sharePrice;
    }

    public int getSharesAvailableToTrade() {
        return sharesAvailableToTrade;
    }

    public void setSharesAvailableToTrade(int sharesAvailableToTrade) {
        this.sharesAvailableToTrade = sharesAvailableToTrade;
    }
}
