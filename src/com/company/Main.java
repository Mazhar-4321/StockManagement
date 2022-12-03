package com.company;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Paths paths = new Paths();
    private final int PRINT_STOCK_REPORT = 2;
    private final int EXIT = -1;
    private final int ADD_MONEY = 3;
    private final int WITHDRAW_MONEY = 4;
    private final int AVAILABLE_BALANCE = 5;
    private final int BUY_SHARES = 6;
    private final int SELL_SHARES = 7;
    StockMarket stockMarket;
    private User user;

    public static void main(String[] args) {
        Main main = new Main();
        main.stockMarket = new StockMarket();
        main.makeMarketReady();
        while (true) {
            System.out.println("Enter 1 for Login , 2 for Register");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    main.login();
                    break;
                case 2:
                    main.registerUser();
                    break;
            }
            if (main.user != null) {
                break;
            }
        }
        main.options(main);
    }

    private void makeMarketReady() {
        stockMarket.loadSharesForMarket(paths.getSharesFilePath());
        stockMarket.loadUsersForMarket(paths.getUsersFilePath());
        stockMarket.loadSharesForUsers(paths.getUsersSharesPath());
    }

    private void registerUser() {
        System.out.println("Enter User Name");
        String userName = scanner.next();
        while (true) {
            if (!stockMarket.checkIfUserExists(userName)) {
                System.out.println("Enter PassWord");
                String password = scanner.next();
                user = stockMarket.registerUser(userName, password);
                return;
            }
            System.out.println("Please Enter A Different User Name");
            userName = scanner.next();
        }
    }

    private void login() {
        System.out.println("enter User Name");
        String userName = scanner.next();
        System.out.println("enter password");
        String password = scanner.next();
        user = stockMarket.login(userName, password);
        if (user == null) {
            System.out.println("Invalid User Name or Password");
            return;
        }

    }

    private void options(Main main) {
        while (true) {
            System.out.println("Press 2 for Print Share Report, 3 for ADD Funds,4 for withdraw Funds,5 for Check Available Balance,6 for Buy Shares,7 for Sell Shares, -1 to Exit");
            int option = scanner.nextInt();
            switch (option) {
                case PRINT_STOCK_REPORT:
                    printStockReport();
                    break;
                case ADD_MONEY:
                    creditMoneyToUsersAccount();
                    break;
                case WITHDRAW_MONEY:
                    debitMoneyFromUsersAccount();
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
        Map<String, Share> shareMap = new HashMap<>();
        if (user.getSharesList().size() == 0) {
            System.out.println("You Don't Have Any Shares , Please Buy Shares First And Try this Option");
            return;
        }
        System.out.println("Your Portfolio");
        shareMap= stockMarket.printSharesList(user.getSharesList());
        System.out.println("Please enter the name of Share U Want to Sell");
        String shareName = scanner.next();
        int quantity = 0;
        while (true) {
            if (!shareMap.containsKey(shareName)) {
                System.out.println("Invalid ShareName,Re enter Share Name");
                shareName = scanner.next();
                continue;
            }
            System.out.println("Please enter quantity");
            quantity = scanner.nextInt();
            if (!((quantity > 0) && (quantity <= shareMap.get(shareName).getQuantity()) && (user.getFunds() >= (shareMap.get(shareName).getPrice() * quantity)))) {
                System.out.println("Invalid Quantity or Your Funds Are Not Sufficient  ");
                continue;
            }
            break;
        }
        stockMarket.sellShares(shareName, quantity, 1, user);
        System.out.println("Operation Successful");
        printDashedLine();
    }

    private void buyShares() {
        System.out.println("Shares Portfolio");
        Map<String, Share> shareMap =stockMarket.printSharesList(stockMarket.getAllShares());
        System.out.println("Please enter the name of Share U Want to Buy");
        String shareName = scanner.next();
        int quantity = 0;
        while (true) {
            if (!shareMap.containsKey(shareName)) {
                System.out.println("Invalid ShareName,Re enter Share Name");
                shareName = scanner.next();
                continue;
            }
            System.out.println("Please enter quantity");
            quantity = scanner.nextInt();
            if (!((quantity > 0) && (quantity <= shareMap.get(shareName).getQuantity()) && (user.getFunds() >= (shareMap.get(shareName).getPrice() * quantity)))) {
                System.out.println("Invalid Quantity or Your Funds Are Not Sufficient  ");
                continue;
            }
            break;
        }
        stockMarket.buyShares(shareName, quantity, 0, user);
        System.out.println("Operation Successful");
        printDashedLine();
    }

    private void printAvailableBalance() {
        System.out.println("Available Balance:" + user.getFunds());
        printDashedLine();
    }

    private void creditMoneyToUsersAccount() {
        System.out.println("Your Current Account Balance :"+user.getFunds());
        System.out.println("Enter Amount You Want to Add");
        double amount = scanner.nextDouble();
        stockMarket.addFundsToUsersAccount(user, amount);
        System.out.println("Your Current Account Balance :"+user.getFunds());
        printDashedLine();
    }

    private void debitMoneyFromUsersAccount() {
        System.out.println("Your Current Account Balance :"+user.getFunds());
        System.out.println("Enter Amount You Want to Withdraw");
        double amount = scanner.nextDouble();
        if (user.getFunds() < amount) {
            System.out.println("WithDrawl Amount Must Be Less Than Or Equal To Available Balance i.e" + user.getFunds());
            printDashedLine();
            return;
        }
        stockMarket.addFundsToUsersAccount(user, -amount);
        System.out.println("Your Current Account Balance :"+user.getFunds());
        printDashedLine();
    }

    private void printStockReport() {
        stockMarket.printUsersStockReport(user);
        printDashedLine();
    }

    private void printDashedLine() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
    }

}
