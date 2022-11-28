package com.company;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private final int ADD_STOCK = 1;
    private final int PRINT_STOCK_REPORT = 2;
    private final int EXIT = -1;
    private final int ADD_MONEY = 3;
    private final int WITHDRAW_MONEY = 4;
    private final int AVAILABLE_BALANCE=5;
    private final int BUY_SHARES=6;
    private final int SELL_SHARES=7;
    private StockPortfolio stockPortfolio= new StockPortfolio();
   private StockAccount stockAccount;

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Management Programme");
        System.out.println("To Start The Stock Market You Have To Add Shares");
        System.out.println("Enter How Many Shares you Want To Add");
        int number= scanner.nextInt();
        CompanySharesManagement companySharesManagement =new CompanySharesManagement();
        for(int i=1;i<=number;i++){
            System.out.println("Please enter Symbol");
            String symbol = scanner.next();
            System.out.println("Please enter share price");
            int sharePrice = scanner.nextInt();
            System.out.println("Please enter quantity");
            int shareQuantity= scanner.nextInt();
            companySharesManagement.addShare(symbol,sharePrice,shareQuantity);
            System.out.println("Share"+i+"Added Successfully");
        }
        System.out.println("Sorry You cant operate Stock Market, Please Open An Account");
        System.out.println("Enter Your Name");
        String name = scanner.next();
        System.out.println("Add Funds To Your Account");
        int funds= scanner.nextInt();
        Main main = new Main();
        main.stockAccount= new StockAccount(name);
        main.stockPortfolio = main.stockAccount.getStockPortfolio();
        main.stockPortfolio.addMoney(funds);
        main.options(main);
    }

    private void options(Main main) {
        while (true) {
            System.out.println("Press 1 to Add Stocks, 2 for Print Stock Report, 3 for ADD Funds,4 for withdraw Funds,5 for Check Available Balance,6 for Buy Shares,7 for Sell Shares, -1 to Exit");
            int option = scanner.nextInt();
            switch (option) {
                case ADD_STOCK:
                    addStocks();
                    break;
                case PRINT_STOCK_REPORT:
                    printStockReport();
                    break;
                case ADD_MONEY:
                    addMoneyToAccount();
                    break;
                case WITHDRAW_MONEY:
                    withdrawMoneyFromAccount();
                    break;
                case AVAILABLE_BALANCE:
                    printAvailableBalance();
                    break;
                case BUY_SHARES:
                    buyShares();
                    break;
                case SELL_SHARES:
                    sellShares();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void sellShares() {
        System.out.println("Enter Share Symbol");
        String symbol = scanner.next();
        for( CompanyShares companyShares : CompanySharesManagement.companySharesList){
            if(companyShares.getSymbol().equals(symbol)){
                System.out.println("Enter Quantity");
                int quantity= scanner.nextInt();
                stockAccount.sellShares(quantity,symbol);
                return;
            }
        }
        System.out.println("Share Does not Exits");
    }

    private void buyShares() {
        System.out.println("Enter Share Symbol");
        String symbol = scanner.next();
       for( CompanyShares companyShares : CompanySharesManagement.companySharesList){
           if(companyShares.getSymbol().equals(symbol)){
               System.out.println("Enter Quantity");
               int quantity= scanner.nextInt();
               stockAccount.buyShares(quantity,symbol);
               return;
           }
       }
        System.out.println("Share Does not Exits");

    }

    private void printAvailableBalance() {
        System.out.println(  stockAccount.valueOf());
    }

    private void withdrawMoneyFromAccount() {
        System.out.println("Enter Amount You Want to Withdraw");
        long amount = scanner.nextLong();
        stockPortfolio.withDrawMoney(amount);
    }

    private void addMoneyToAccount() {
        System.out.println("Enter Amount You Want to Add");
        long amount = scanner.nextLong();
        stockPortfolio.addMoney(amount);
    }

    private void printStockReport() {
        stockPortfolio.printStockReport();
    }

    private void addStocks() {
        System.out.println("Enter Number Of Stocks You Want to Add");
        int number = scanner.nextInt();
        for (int i = 1; i <= number; i++) {
            System.out.printf("Enter Stock %d Details\n", i);
            System.out.println("Enter Share Name");
            String shareName = scanner.next();
            System.out.println("Enter Share Price");
            int sharePrice = scanner.nextInt();
            System.out.println("Enter Shares Quantity");
            int sharesQuantity = scanner.nextInt();
            Stock stock = new Stock();
            stock.setNoOfShares(sharesQuantity);
            stock.setShareName(shareName);
            stock.setSharePrice(sharePrice);
            if (stockPortfolio.addStock(stock)) {
                i--;
            }
        }
    }
}
