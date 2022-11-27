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
    private StockAccountManagement stockAccountManagement;

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Management Programme");
        Main main = new Main();
        main.stockAccountManagement = new StockAccountManagement();
        main.options(main);
    }

    private void options(Main main) {
        while (true) {
            System.out.println("Press 1 to Add Stocks, 2 for Print Stock Report, 3 for ADD Funds,4 for withdraw Funds,5 for Check Available Balance -1 to Exit");
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
                case EXIT:
                    return;
            }
        }
    }

    private void printAvailableBalance() {
        stockAccountManagement.printAvailableBalance();
    }

    private void withdrawMoneyFromAccount() {
        System.out.println("Enter Amount You Want to Withdraw");
        long amount = scanner.nextLong();
        stockAccountManagement.withDrawMoney(amount);
    }

    private void addMoneyToAccount() {
        System.out.println("Enter Amount You Want to Add");
        long amount = scanner.nextLong();
        stockAccountManagement.addMoney(amount);
    }

    private void printStockReport() {
        stockAccountManagement.printStockReport();
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
            if (stockAccountManagement.addStock(stock)) {
                i--;
            }
        }
    }
}
