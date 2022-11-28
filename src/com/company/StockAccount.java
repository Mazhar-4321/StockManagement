package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockAccount {
    private StockPortfolio stockPortfolio = new StockPortfolio();
    private String customerName;
    private List<CompanyShares> companySharesList = new ArrayList<>();

    public StockAccount(String fileName) {
        customerName = fileName;
        companySharesList = CompanySharesManagement.companySharesList;
    }

    public StockPortfolio getStockPortfolio() {
        return stockPortfolio;
    }

    double valueOf() {
        return stockPortfolio.getAvailableBalance();
    }

    @Deprecated
    void buy(int amount, String symbol) {
        if (stockPortfolio.getAvailableBalance() < amount) {
            System.out.println("Your Available Balance is :" + stockPortfolio.getAvailableBalance() + "and Specified Amount is:" + amount);
            return;
        }
        if (companySharesList.size() == 0) {
            companySharesList = CompanySharesManagement.companySharesList;
        }
        CompanyShares companyShares = new CompanyShares();
        boolean flag = false;
        List<CompleteShareInfo> completeShareInfoList = CompanySharesManagement.sharesInfoMap.get(symbol);
        CompleteShareInfo completeShareInfo = new CompleteShareInfo();
        for (CompanyShares companyShares1 : companySharesList) {
            if (companyShares1.getSymbol().equals(symbol)) {
                companyShares.setSymbol(companyShares1.getSymbol());
                companyShares.setSharesAvailableToTrade(companyShares1.getSharesAvailableToTrade());
                companyShares.setSharePrice(companyShares1.getSharePrice());
                flag = true;
                completeShareInfo.setShareQuantity(amount);
                completeShareInfo.setShareHolder(customerName);
                completeShareInfo.setTransaction(Transaction.BUY);
                completeShareInfo.setDateOfTransaction(new Date());
                completeShareInfoList.add(completeShareInfo);
                break;
            }
        }
        if (flag) {
            int amouuntToBeDeducted = (amount * companyShares.getSharePrice());
            System.out.println(amouuntToBeDeducted);
            stockPortfolio.addMoney(-(amouuntToBeDeducted));
            Stock stock = stockPortfolio.isStockAvailable(symbol);
            if (stock == null) {
                stock = new Stock();
            }
            stock.setShareName(symbol);
            stock.setNoOfShares(stock.getNoOfShares() + amount);
            completeShareInfo.setShareQuantity(completeShareInfo.getShareQuantity() - stock.getNoOfShares());
            stock.setSharePrice(companyShares.getSharePrice());
            if (stock == null) {
                stockPortfolio.addStock(stock);
            } else
                stockPortfolio.setList(stock);
        }

    }

    void buyShares(int amount, String symbol) {
        int sharesAvailableToTrade = 0;
        int sharePrice = 0;
        for (CompanyShares c : CompanySharesManagement.companySharesList) {
            if (c.getSymbol().equals(symbol)) {
                sharesAvailableToTrade = c.getSharesAvailableToTrade();
                sharePrice = c.getSharePrice();
            }
        }
        for (Stock stock : stockPortfolio.getStockList()) {
            if (stock.getShareName().equals(symbol) && amount <= sharesAvailableToTrade) {
                stock.setNoOfShares(stock.getNoOfShares() + amount);
                stockPortfolio.addMoney(-(amount * stock.getSharePrice()));
                stockPortfolio.setList(stock);
                return;
            }
        }
        Stock stock = new Stock();
        stock.setNoOfShares(amount);
        stock.setShareName(symbol);
        stock.setSharePrice(sharePrice);
        stockPortfolio.addStock(stock);
        stockPortfolio.addMoney(-(amount * sharePrice));
        System.out.println("");
    }

    void sellShares(int amount, String symbol) {
        int sharesAvailableToTrade = 0;
        int sharePrice = 0;
        for (CompanyShares c : CompanySharesManagement.companySharesList) {
            if (c.getSymbol().equals(symbol)) {
                sharesAvailableToTrade = c.getSharesAvailableToTrade();
                sharePrice = c.getSharePrice();
            }
        }
        for (Stock stock : stockPortfolio.getStockList()) {
            if (stock.getShareName().equals(symbol) && amount <= sharesAvailableToTrade) {
                stock.setNoOfShares(stock.getNoOfShares() - amount);
                stockPortfolio.addMoney((amount * stock.getSharePrice()));
                stockPortfolio.setList(stock);
                return;
            }
        }
        System.out.println("");
    }

    @Deprecated
    void sell(int amount, String symbol) {
        List<CompleteShareInfo> completeShareInfoList = CompanySharesManagement.sharesInfoMap.get(symbol);
        CompleteShareInfo completeShareInfo = new CompleteShareInfo();
        for (Stock stock : stockPortfolio.getStockList()) {
            if (stock.getShareName().equals(symbol) && amount <= stock.getNoOfShares()) {
                stockPortfolio.addMoney(stock.getSharePrice() * amount);
                stock.setNoOfShares(stock.getNoOfShares() - amount);
                completeShareInfo.setShareQuantity(amount);
                completeShareInfo.setShareHolder(customerName);
                completeShareInfo.setTransaction(Transaction.SELL);
                completeShareInfo.setDateOfTransaction(new Date());
                completeShareInfo.setShareQuantity(completeShareInfo.getShareQuantity() + amount);
                completeShareInfoList.add(completeShareInfo);
            }
        }

    }

    void save(String fileName) {
        customerName = fileName;
    }

    void printReport() {
        System.out.println("Stock Portfolio Report");
        stockPortfolio.printStockReport();
    }
}
