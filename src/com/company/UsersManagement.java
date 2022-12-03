package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UsersManagement {
    private Map<String, User> usersMap = new HashMap<>();
    private Set<String> keySet = new LinkedHashSet<>();
    private String path;

    UsersManagement() {

    }

    @Deprecated
    UsersManagement(String path) {
        loadUsersFromFile(path);
    }

    public User checkForUser(String username, String password) {
        if (checkUserNameAvailability(username)) {
            return usersMap.get(username);
        }
        return null;
    }

    private void updateUsersFile(User user) {
        File file = new File(path);
        FileWriter fr = null;
        String text = user.getUserName() + "," + user.getPassword() + "," + user.getFunds();
        try {
            fr = new FileWriter(file, true);
            fr.write(text);
            fr.write("\r\n");

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

    public void updateUsersFundInFile(User user, String path) {
        ArrayList<String> stringList = new ArrayList<>();
        try {
            File myObj = new File(path);

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] userArray = data.split(",");
                String userName = userArray[0];
                String password = userArray[1];
                String funds = userArray[2];
                if (userName.equals(user.getUserName())) {
                    String modifiedData = user.getUserName() + "," + user.getPassword() + "," + user.getFunds();
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
        String text = user.getUserName() + "," + user.getPassword() + "," + user.getFunds();
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

    public boolean checkUserNameAvailability(String userName) {
        return keySet.contains(userName);
    }

    public void loadUsersFromFile(String fileName) {
        this.path = fileName;
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (count > 0) {
                    String[] userArray = data.split(",");
                    String userName = userArray[0];
                    String password = userArray[1];
                    double funds = Double.parseDouble(userArray[2]);
                    usersMap.put(userName, new User(userName, password, funds));
                    keySet.add(userName);
                }
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addSharesFromFileToUsersProfile(String path) {
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String userName = (String) it.next();
            loadShares(path, userName);
        }
    }

    public void loadShares(String path, String userName) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (count > 0) {
                    String[] sharesInfoArray = data.split(",");
                    //UserName,ShareName,ShareQuantity,SharePrice
                    String userName1 = sharesInfoArray[0];
                    String shareName1 = sharesInfoArray[1];
                    String shareQuantity = sharesInfoArray[2];
                    String sharePrice = sharesInfoArray[3];
                    if (userName1.equals(userName)) {
                        List<Share> sharesList = usersMap.get(userName1).getSharesList();
                        sharesList.add(new Share(shareName1, Double.parseDouble(sharePrice), Integer.parseInt(shareQuantity)));
                    }

                }
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addFundsToUsersAccount(User user, double amount) {
        user.setFunds(user.getFunds() + amount);
        updateUsersFundInFile(user, new Paths().getUsersFilePath());
    }

    public User registerUser(String userName, String password) {
        User user = new User(userName, password, 0.0);
        user.setSharesList(new ArrayList<>());
        usersMap.put(userName, user);
        keySet.add(userName);
        updateUsersFile(user);
        return user;
    }
}
