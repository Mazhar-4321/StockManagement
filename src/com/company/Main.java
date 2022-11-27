package com.company;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private final int ADD_STOCK = 1;
    private final int PRINT_STOCK_REPORT = 2;
    private final int EXIT = -1;
    private StockAccountManagement stockAccountManagement;

    public static void main(String[] args) {
        System.out.println("Welcome to Stock Management Programme");
        Main main = new Main();
        main.stockAccountManagement = new StockAccountManagement();
        main.options(main);
    }

    private void options(Main main) {
        while (true) {
            System.out.println("Press 1 to Add Stocks, 2 for Print Stock Report, -1 to Exit");
            int option = scanner.nextInt();
            switch (option) {
                case ADD_STOCK:
                    addStocks();
                    break;
                case PRINT_STOCK_REPORT:
                    printStockReport();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void printStockReport() {
        stockAccountManagement.printStockReport();
    }

    private void addStocks() {
        System.out.println("Enter Number Of Stocks You Want to Add");
        int number = scanner.nextInt();
        for (int i = 1; i <= number; i++) {
            System.out.printf("Enter Stock %d Details\n",i);
            System.out.println("Enter Share Name");
            String shareName= scanner.next();
            System.out.println("Enter Share Price");
            int sharePrice=scanner.nextInt();
            System.out.println("Enter Shares Quantity");
            int sharesQuantity=scanner.nextInt();
            Stock stock = new Stock();
            stock.setNoOfShares(sharesQuantity);
            stock.setShareName(shareName);
            stock.setSharePrice(sharePrice);
           if( stockAccountManagement.addStock(stock)){
               i--;
           }
        }
    }
}
