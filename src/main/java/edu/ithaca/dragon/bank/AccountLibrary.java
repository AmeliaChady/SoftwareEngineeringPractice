package edu.ithaca.dragon.bank;


import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class AccountLibrary {
    protected Map<String, BankAccount> accounts;

    public AccountLibrary(){
        accounts = new TreeMap<String, BankAccount>(); // Should look into best one to use!
    }


    /**
     * Totals the balances across all accounts.
     * @return the total
     */
    public double CalcTotalAssets(){
        return -1;
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
        return null;
    }

    /**
     * Freezes account
     * @param id account id to freeze
     */
    public void freezeAccount(String id){

    }

    /**
     * unfreezes account
     * @param id account id to unfreeze
     */
    public void unfreezeAccount(String id){}

    /**
     * Calls every accounts update function
     */
    public void updateAccounts(){}
}
