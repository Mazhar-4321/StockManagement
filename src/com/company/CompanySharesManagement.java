package com.company;

import java.util.*;

public class CompanySharesManagement {
    public static List<CompanyShares> companySharesList = new ArrayList<>();
    public static Map<String,List<CompleteShareInfo>> sharesInfoMap = new HashMap<>();
    List<CompanyShares> getCompanySharesList(){
        return companySharesList;
    }
    public void addShare(String symbol, int sharePrice, int sharesAvailableToTrade) {
        CompanyShares companyShares = new CompanyShares();
        companyShares.setSharePrice(sharePrice);
        companyShares.setSharesAvailableToTrade(sharesAvailableToTrade);
        companyShares.setSymbol(symbol);
        if (!isShareRegistered(symbol)) {
            companySharesList.add(companyShares);
            List<CompleteShareInfo> completeShareInfoList= new ArrayList<>();
            CompleteShareInfo completeShareInfo = new CompleteShareInfo();
            completeShareInfo.setShareHolder(symbol);
            completeShareInfo.setShareQuantity(sharesAvailableToTrade);
            completeShareInfo.setDateOfTransaction(new Date());
            completeShareInfoList.add(completeShareInfo);
            sharesInfoMap.put(symbol,completeShareInfoList);
            return;
        }
        System.out.println("Symbol cant be used");
    }

    boolean isShareRegistered(String symbol) {
        for (CompanyShares companyShares : companySharesList) {
            if (companyShares.getSymbol().equals(symbol))
                return true;
        }
        return false;
    }
}
