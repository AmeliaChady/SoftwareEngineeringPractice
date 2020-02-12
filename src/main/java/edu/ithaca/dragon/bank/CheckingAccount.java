package edu.ithaca.dragon.bank;


import java.util.LinkedList;

import static edu.ithaca.dragon.bank.Utilities.*;

public class CheckingAccount extends BankAccount{

    /**
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    public CheckingAccount(String accountID, double startingBalance, String password){
        if(!isAccountIDValid(accountID)){
            throw new IllegalArgumentException("Account ID "+accountID+" is Invalid!");
        }
        if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance: " + startingBalance + " is invalid, cannot create account");
        }
        if(!isPasswordValid(password)){
            throw new IllegalArgumentException("Password invalid");
        }
        this.accountID = accountID;
        this.balance = startingBalance;
        this.history = new LinkedList<>();
        this.password = password;
        this.loggedIn = false;

    }

    @Override
    public void update() {
        // Is nothing, as it is not needed for specific class usage.
        // Needed to allow for usability
    }
}


