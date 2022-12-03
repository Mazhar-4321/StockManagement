package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SharesManagement {
    private Map<String, Share> sharesMap;
    private List<Share> sharesList;
    private String path;

    SharesManagement() {

    }

    @Deprecated
    SharesManagement(String path) {
        loadSharesFromFile(path);
    }

    public List<Share> getAllShares() {
        return sharesList;
    }

    public void updateShareInfo(String shareName, int quantity, int offset, User user) {
        boolean flag = true;
        if (offset == 0) {
            flag = validateUserForSharePurchase(shareName, quantity, user);
        }
        if (offset == 1) {
            flag = validateUserToSellShare(shareName, quantity, user);
        }
        if (!flag) {
            System.out.println("Operation Failed, Please Check Shares Portfolio and Try Again");
            return;
        }
        Share share = sharesMap.get(shareName);
        List<Share> userSharesList = user.getSharesList();
        Share share1 = isShareAvailableInList(shareName, userSharesList);
        if (share1 != null) {
            if (offset == 0) {
                share1.setQuantity(share1.getQuantity() + quantity);
                user.setFunds(user.getFunds() - (quantity * share.getPrice()));
                share.setQuantity(share.getQuantity() - quantity);
            } else {
                share1.setQuantity(share1.getQuantity() - quantity);
                user.setFunds(user.getFunds() + (quantity * share.getPrice()));
                share.setQuantity(share.getQuantity() + quantity);
            }
        } else {
            userSharesList.add(new Share(shareName, share.getPrice(), quantity));
            user.setFunds(user.getFunds() - (quantity * share.getPrice()));
            share.setQuantity(share.getQuantity() - quantity);
        }
        updateSharesFile(shareName);
        updateUsersStockRecordFile(shareName, quantity, offset, user);
    }

    private Share isShareAvailableInList(String shareName, List<Share> sharesList) {
        for (Share share : sharesList) {
            if (share.getName().equals(shareName)) {
                return share;
            }
        }
        return null;
    }

    public boolean validateUserForSharePurchase(String shareName, int quantity, User user) {
        int shareQuantityAvailableForPurchase = sharesMap.get(shareName).getQuantity();
        double currentSharePrice = sharesMap.get(shareName).getPrice();
        double usersAvailableFunds = user.getFunds();
        double billAmount = quantity * currentSharePrice;
        if (quantity <= shareQuantityAvailableForPurchase && usersAvailableFunds >= billAmount) {
            return true;
        }
        return false;
    }

    public boolean validateUserToSellShare(String shareName, int quantity, User user) {
        int maxShareSellQuantityOfUser = user.getSharesList().stream().filter(q -> q.getName().equals(shareName)).findFirst().get().getQuantity();
        if (quantity <= maxShareSellQuantityOfUser) {
            return true;
        }
        return false;
    }

    private void updateUsersStockRecordFile(String shareName, int quantity, int offset, User user) {
        ArrayList<String> strings = new ArrayList<>();
        boolean isUserAvailable = false;
        boolean isShareAvailable = false;
        try {
            File myObj = new File("D:\\Maven WorkSpace\\StockManagement\\src\\resources\\usersStockRecord.csv");
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (count > 0) {
                    String[] sharesInfoArray = data.split(",");
                    String userName = sharesInfoArray[0];
                    String shareName1 = sharesInfoArray[1];
                    String shareQuantity = sharesInfoArray[2];
                    String sharePrice = sharesInfoArray[3];
                    if (user.getUserName().equals(userName) && shareName.equals(shareName1)) {
                        isShareAvailable = true;
                        isUserAvailable = true;
                        int modifiedQuantity = 0;
                        if (offset == 0) {
                            modifiedQuantity = Integer.parseInt(shareQuantity) + quantity;
                        } else {
                            modifiedQuantity = Integer.parseInt(shareQuantity) - quantity;
                        }
                        String modifiedData = userName + "," + shareName1 + "," + modifiedQuantity + "," + sharePrice;
                        strings.add(modifiedData);
                    } else {
                        strings.add(data);
                    }
                } else {
                    strings.add(data);
                }
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if (isShareAvailable == false && isUserAvailable == false) {
            String initializedData = user.getUserName() + "," + shareName + "," + quantity + "," + sharesMap.get(shareName).getPrice();
            strings.add(initializedData);
        }
        File file = new File("D:\\Maven WorkSpace\\StockManagement\\src\\resources\\usersStockRecord.csv");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, false);
            for (int i = 0; i < strings.size(); i++) {
                fr.write(strings.get(i));
                fr.write("\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void updateSharesFile(String shareName) {
        String text = "";
        ArrayList<String> stringList = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] sharesInfoArray = data.split(",");
                String name = sharesInfoArray[0];
                if (name.equals(shareName)) {
                    String modifiedData = shareName + "," + sharesMap.get(shareName).getPrice() + "," + sharesMap.get(shareName).getQuantity();
                    stringList.add(modifiedData);
                } else {
                    stringList.add(data);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        File file = new File(path);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, false);
            for (int i = 0; i < stringList.size(); i++) {
                fr.write(stringList.get(i));
                fr.write("\r\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void loadSharesFromFile(String path) {
        this.path = path;
        sharesMap = new HashMap<>();
        sharesList = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (count > 0) {
                    String[] sharesInfoArray = data.split(",");
                    String name = sharesInfoArray[0];
                    double price = Double.parseDouble(sharesInfoArray[1]);
                    int quantity = Integer.parseInt(sharesInfoArray[2]);
                    Share share = new Share(name, price, quantity);
                    sharesMap.put(name, share);
                    sharesList.add(share);
                }
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
