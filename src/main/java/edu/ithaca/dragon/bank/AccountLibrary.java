package edu.ithaca.dragon.bank;

import java.util.LinkedList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static edu.ithaca.dragon.bank.Utilities.isAccountIDValid;
import static edu.ithaca.dragon.bank.Utilities.isAmountValid;

public class AccountLibrary {
    protected Map<String, BankAccount> accounts;

    public AccountLibrary(){
        accounts = new TreeMap<String, BankAccount>(); // Should look into best one to use!
    }

    /**
     * @param accountID
     * @return balance of account associated with accountID
     */
    public double getBalance(String accountID){
        BankAccount account = accounts.get(accountID);
        double balance = account.getBalance();
        return balance;
    }

    /**
     * Creates a checking account with accountID and starting balance given and adds it to account list
     * @param accountID
     * @param startingBalance
     */
    public void createCheckingAccount(String accountID, double startingBalance){
        CheckingAccount ca = new CheckingAccount(accountID, startingBalance);
    }

    /**
     * Creates a savings account with accountID, starting balance, and interest rate given and adds it to account list
     * @param accountID
     * @param startingBalance
     * @param interest
     */
    public void createSavingsAccount(String accountID, double startingBalance, double interest){
        SavingsAccount sa = new SavingsAccount(accountID, startingBalance, interest);
    }

    /**
     * Deletes account associated with accountID given from account list
     * @param accountID
     */
    public void closeAccount(String accountID){
        accounts.remove(accountID);
        //lastCheckedHistory.remove(accountID);
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

