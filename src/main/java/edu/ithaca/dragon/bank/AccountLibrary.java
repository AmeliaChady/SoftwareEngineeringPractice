package edu.ithaca.dragon.bank;

import java.util.*;

import static edu.ithaca.dragon.bank.Utilities.isAccountIDValid;
import static edu.ithaca.dragon.bank.Utilities.isAmountValid;

public class AccountLibrary {
    protected Map<String, BankAccount> accounts;
    protected Map<String, Integer> lastCheckedHistory;

    public AccountLibrary(){
        accounts = new TreeMap<String, BankAccount>(); // Should look into best one to use!
        lastCheckedHistory = new TreeMap<String, Integer>();
    }


    public double getBalance(String accountID){
        BankAccount account = accounts.get(accountID);
        double balance = account.getBalance();
        return balance;
    }

    public void createCheckingAccount(String accountID, double startingBalance){
        CheckingAccount ca = new CheckingAccount(accountID, startingBalance);
    }

    public void closeAccount(String accountID, String accountType){
        if (accountType != "savings" || accountType != "checking"){
            throw new IllegalArgumentException("Account must be either 'checking' or 'savings'");
        }
        if(!isAccountIDValid(accountID)){
            throw new IllegalArgumentException("Account ID "+accountID+" is Invalid!");
        }

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
            lastCheckedHistory.put(id, history.size()-1);
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

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, AccountFrozenException {
        BankAccount ba = accounts.get(acctId);
        ba.withdraw(amount);
    }

    public void deposit(String acctId, double amount) throws AccountFrozenException{
        BankAccount ba = accounts.get(acctId);
        ba.deposit(amount);
    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException, AccountFrozenException {
        BankAccount ba1 = accounts.get(acctIdToWithdrawFrom);
        BankAccount ba2 = accounts.get(acctIdToDepositTo);
        ba1.transfer(ba2, amount);
    }

    public List<Double> transactionHistory(String acctId) {
        BankAccount ba = accounts.get(acctId);
        return ba.getHistory();
    }
}

//when i delete the pair in the map delete