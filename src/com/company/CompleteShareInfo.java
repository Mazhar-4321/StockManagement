package com.company;

import java.util.Date;

public class CompleteShareInfo {
    private Date dateOfTransaction;
    private int shareQuantity;
    private String shareHolder;
    private Transaction transaction;

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public int getShareQuantity() {
        return shareQuantity;
    }

    public void setShareQuantity(int shareQuantity) {
        this.shareQuantity = shareQuantity;
    }

    public String getShareHolder() {
        return shareHolder;
    }

    public void setShareHolder(String shareHolder) {
        this.shareHolder = shareHolder;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
