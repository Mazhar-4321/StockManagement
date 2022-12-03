package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class User {
    private String userName;
    private String password;
    private double funds;
    private List<Share> sharesList = new ArrayList<>();

    public User(String userName, String password, double funds) {
        this.userName = userName;
        this.password = password;
        this.funds = funds;
    }

    public List<Share> getSharesList() {
        return sharesList;
    }

    public void setSharesList(List<Share> sharesList) {
        this.sharesList = sharesList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void loadShares(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (count > 0) {
                    String[] sharesInfoArray = data.split(",");
                    //UserName,ShareName,ShareQuantity,SharePrice
                    String userName = sharesInfoArray[0];
                    String shareName1 = sharesInfoArray[1];
                    String shareQuantity = sharesInfoArray[2];
                    String sharePrice = sharesInfoArray[3];
                    if (userName.equals(this.userName)) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.funds, funds) == 0 && Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, funds);
    }
}
