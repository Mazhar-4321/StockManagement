package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockMarket {
    private SharesManagement sharesManagement;
    private UsersManagement usersManagement;

    StockMarket() {
        sharesManagement = new SharesManagement();
        usersManagement = new UsersManagement();
    }

    public void loadSharesForMarket(String fileName) {
        sharesManagement.loadSharesFromFile(fileName);
    }

    public void loadUsersForMarket(String fileName) {
        usersManagement.loadUsersFromFile(fileName);
    }

    public void loadSharesForUsers(String fileName) {
        usersManagement.addSharesFromFileToUsersProfile(fileName);
    }

    public User login(String userName, String password) {
        return usersManagement.checkForUser(userName, password);
    }

    public void buyShares(String shareName, int quantity, int offset, User user) {
        sharesManagement.updateShareInfo(shareName, quantity, offset, user);
        usersManagement.updateUsersFundInFile(user, new Paths().getUsersFilePath());
    }

    public void sellShares(String shareName, int quantity, int offset, User user) {
        sharesManagement.updateShareInfo(shareName, quantity, offset, user);
        usersManagement.updateUsersFundInFile(user, new Paths().getUsersFilePath());
    }

    public List<Share> getAllShares() {
        return sharesManagement.getAllShares();
    }

    public void printUsersStockReport(User user) {
        System.out.println(giveFormattedString("Share Name") + giveFormattedString("Share Price") + giveFormattedString("No. Of Shares") + giveFormattedString("Value"));
        double totalValue = 0;
        for (Share share : user.getSharesList()) {
            totalValue += share.getPrice() * share.getQuantity();
            System.out.println(giveFormattedString(share.getName()) + giveFormattedString(share.getPrice() + "") +
                    giveFormattedString(share.getQuantity() + "") +
                    giveFormattedString((share.getQuantity() * share.getPrice()) + ""));
        }
        System.out.println(giveFormattedString("Total Value=" + totalValue + ""));
    }

    private String giveFormattedString(String string) {
        String whiteSpaces = "                         ";
        String stringWithWhiteSpaces = string + whiteSpaces.substring(string.length());
        return stringWithWhiteSpaces;
    }

    public void addFundsToUsersAccount(User user, double amount) {
        usersManagement.addFundsToUsersAccount(user, amount);
    }

    public boolean checkIfUserExists(String userName) {
        return usersManagement.checkUserNameAvailability(userName);
    }

    public User registerUser(String userName, String password) {
        return usersManagement.registerUser(userName, password);
    }

    public Map<String, Share> printSharesList(List<Share> sharesList) {
        Map<String, Share> shareMap = new HashMap<>();
        System.out.println(giveFormattedString("Share Name") + giveFormattedString("Share Price") + giveFormattedString("No. Of Shares"));
        for (Share share : sharesList) {
            System.out.println(giveFormattedString(share.getName()) + giveFormattedString(share.getPrice() + "") +
                    giveFormattedString(share.getQuantity() + ""));
            shareMap.put(share.getName(), share);
        }
        return shareMap;
    }
}
