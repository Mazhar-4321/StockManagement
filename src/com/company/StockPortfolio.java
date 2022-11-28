package com.company;

import java.util.ArrayList;
import java.util.List;

public class StockPortfolio {
    private List<Stock> stockList = new ArrayList<>();
    private Long totalStocksValue;
    private AccountBalance accountBalance;

    StockPortfolio() {
        stockList = new ArrayList<>();
        totalStocksValue = 0l;
        accountBalance = new AccountBalance();
    }
    public List<Stock> getStockList(){
        return stockList;
    }
    public void withDrawMoney(long amount) {
        if (amount <= 0) {
            System.out.println("Minimum Debit Amount Needs to be 1");
            return;
        }
        if (amount > accountBalance.getAvailableAmount()) {
            System.out.println("Amount Must be <=" + accountBalance.getAvailableAmount());
            return;
        }
        accountBalance.setAvailableAmount(accountBalance.getAvailableAmount() - amount);
        System.out.println("Amount Remaininig After Debit:" + accountBalance.getAvailableAmount());
    }

    public void addMoney(long amount) {
        accountBalance.setAvailableAmount(accountBalance.getAvailableAmount() + amount);
    }
    public void setList(Stock stock){
        for(int i=0;i<stockList.size();i++){
            if(stockList.get(i).getShareName().equals(stock.getShareName())){
                stockList.set(i,stock);
                return;
            }
        }
    }
    public boolean addStock(Stock stock) {
        if (isStockAvailable(stock) != null) {
            stockList.add(stock);
            totalStocksValue += stock.getNoOfShares() * stock.getSharePrice();
            return false;
        }
        return true;
    }

    private Stock isStockAvailable(Stock stock) {
        for (Stock stock1 : stockList) {
            if (stock1.getShareName().equals(stock.getShareName())) {
                System.out.println("Share Already Exists");
                return null;
            }
        }
        return stock;
    }
 public Stock isStockAvailable(String symbol){
     for (Stock stock1 : stockList) {
         if (stock1.getShareName().equals(symbol)) {
            // System.out.println("Share Already Exists");
             //stock1=stock;
             return stock1;
         }
     }
     return null;
 }
    public void printStockReport() {
        System.out.println("StockName  SharePrice No.OfShares  Value");
        for (Stock stock : stockList) {
            System.out.println(stock.getShareName() + "   " + stock.getSharePrice() + "  " + stock.getNoOfShares() + " " + (stock.getSharePrice() * stock.getNoOfShares()));
        }
        int totalStocksValue=0;
        for(Stock s : stockList){
            totalStocksValue+=s.getNoOfShares()*s.getSharePrice();
        }
        System.out.println("total Value=" + totalStocksValue);
    }

    public void printAvailableBalance() {
        System.out.println("Available Balance="+accountBalance.getAvailableAmount());
    }
    public double getAvailableBalance(){
        return accountBalance.getAvailableAmount();
    }
}