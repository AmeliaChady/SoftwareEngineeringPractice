package edu.ithaca.dragon.bank;


import java.util.LinkedList;
import java.util.Map;

import static edu.ithaca.dragon.bank.Utilities.isAccountIDValid;
import static edu.ithaca.dragon.bank.Utilities.isAmountValid;

public class AccountLibrary {
    private Map<String, BankAccount> accounts;


    public double getBalance(String accountID){
        BankAccount account = accounts.get(accountID);
        double balance = account.getBalance();
        return balance;
    }

    public void createCheckingAccount(String accountID, String password, double startingBalance, String accountType){
        if (accountType != "savings" || accountType != "checking"){
            throw new IllegalArgumentException("Account must be either 'checking' or 'savings'");
        }
        if(!isAccountIDValid(accountID)){
            throw new IllegalArgumentException("Account ID "+accountID+" is Invalid!");
        }
        if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance: " + startingBalance + " is invalid, cannot create account");
        }
        this.accountID = accountID;
        this.balance = startingBalance;
        this.history = new LinkedList<>();
    }

    public void closeAccount(String accountID, String accountType){
        if (accountType != "savings" || accountType != "checking"){
            throw new IllegalArgumentException("Account must be either 'checking' or 'savings'");
        }
        if(!isAccountIDValid(accountID)){
            throw new IllegalArgumentException("Account ID "+accountID+" is Invalid!");
        }

    }
}

//when i delete the pair in the map delete