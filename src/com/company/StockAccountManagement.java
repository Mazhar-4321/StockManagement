package com.company;

import java.util.ArrayList;
import java.util.List;

public class StockAccountManagement {
    private List<Stock> stockList = new ArrayList<>();
    private Long totalStocksValue = 0l;

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

    public void printStockReport() {
        System.out.println("StockName  SharePrice No.OfShares  Value");
        for (Stock stock : stockList) {
            System.out.println(stock.getShareName() + "   " + stock.getSharePrice() + "  " + stock.getNoOfShares()+" "+(stock.getSharePrice()*stock.getNoOfShares()));
        }
        System.out.println("total Value="+totalStocksValue);
    }
}
