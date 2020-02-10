package edu.ithaca.dragon.bank;


import java.util.*;

public class AccountLibrary {
    protected Map<String, BankAccount> accounts;
    protected Map<String, Integer> lastCheckedHistory;

    public AccountLibrary(){
        accounts = new TreeMap<String, BankAccount>(); // Should look into best one to use!
        lastCheckedHistory = new TreeMap<String, Integer>();
    }


    /**
     * Totals the balances across all accounts.
     * @return the total
     */
    public double calcTotalAssets(){
        Iterator<BankAccount> accountIterator = accounts.values().iterator();
        double total = 0;
        while (accountIterator.hasNext()){
            total += accountIterator.next().getBalance();
        }
        return total;
    }

    /**
     * Iterates through the library and finds all accounts that have one or more of the following:
     * n : transactions since last time this function was called
     * if any |n| withdraws more than x
     * if length of n > y
     * if |total amount of n| > z
     * @param maxSingleWithdrawl x
     * @param maxTotalWithdrawls y
     * @param numberOfWithdrawls z
     * @return account ids of failed accounts
     */
    public Collection<String> findSuspiciousAccounts(
            double maxSingleWithdrawl,
            int numberOfWithdrawls,
            double maxTotalWithdrawls){

        Iterator<String> ids = accounts.keySet().iterator();
        List<String> strings = new LinkedList<>();

        while (ids.hasNext()){
            String id = ids.next();
            BankAccount ba = accounts.get(id);
            Integer index = lastCheckedHistory.get(id);
            List<Double> history = ba.getHistory();
            boolean inTrouble = false;

            if(index == null){
                index = -1;
            }

            if(history.size() + 1 - index > numberOfWithdrawls){
                inTrouble = true;
            }else{
                double total = 0;
                for(int i = index + 1; i < history.size(); i++){
                    double amount = history.get(i);
                    total += amount;
                    if(Math.abs(amount) > maxSingleWithdrawl){
                        inTrouble = true;
                    }
                }
                if(Math.abs(total) > maxTotalWithdrawls){
                    inTrouble = true;
                }
            }

            if(inTrouble){
                strings.add(id);
            }
        }
        return strings;
    }

    /**
     * Freezes account
     * @param id account id to freeze
     */
    public void freezeAccount(String id){
        BankAccount ba = accounts.get(id);
        if(ba != null){
            ba.freezeAccount();
        }
    }

    /**
     * unfreezes account
     * @param id account id to unfreeze
     */
    public void unfreezeAccount(String id){
        BankAccount ba = accounts.get(id);
        if(ba != null){
            ba.unfreezeAccount();
        }
    }

    /**
     * Calls every accounts update function
     */
    public void updateAccounts(){
        Iterator<BankAccount> accountIterator = accounts.values().iterator();
        while (accountIterator.hasNext()){
            accountIterator.next().update();
        }
    }
}
