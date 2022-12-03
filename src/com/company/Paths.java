package com.company;

public class Paths {
    private String sharesFilePath;
    private String usersFilePath;
    private String sharesHistoryFilePath;
    private String usersSharesPath;
    Paths(){
        this.sharesFilePath="D:\\Maven WorkSpace\\StockManagement\\src\\resources\\shares.csv";
        this.usersFilePath="D:\\Maven WorkSpace\\StockManagement\\src\\resources\\users.csv";
        this.sharesHistoryFilePath="D:\\Maven WorkSpace\\StockManagement\\src\\resources\\sharesHistory.csv";
        this.usersSharesPath ="D:\\Maven WorkSpace\\StockManagement\\src\\resources\\usersStockRecord.csv";
    }

    public String getSharesFilePath() {
        return sharesFilePath;
    }

    public String getUsersFilePath() {
        return usersFilePath;
    }

    public String getSharesHistoryFilePath() {
        return sharesHistoryFilePath;
    }

    public String getUsersSharesPath() {
        return usersSharesPath;
    }
}
