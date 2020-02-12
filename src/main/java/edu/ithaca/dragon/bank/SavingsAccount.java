package edu.ithaca.dragon.bank;

import java.util.LinkedList;

import static edu.ithaca.dragon.bank.Utilities.*;

public class SavingsAccount extends BankAccount{
    double interest;

    /**
     * Creates a savings account with daily interest
     * @throws IllegalArgumentException if accountID, interest or balance is invalid
     */
    SavingsAccount(String accountid, double startingBalance, String password, double interest){
        if(!isAccountIDValid(accountid)){
            throw new IllegalArgumentException("Account ID invalid");
        }else if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance invalid");
        }else if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest invalid");
        } else if(!isPasswordValid(password)){
            throw new IllegalArgumentException("Password invalid");
        }

        this.accountID = accountid;
        this.balance = startingBalance;
        this.history = new LinkedList<>();
        this.interest = interest;
        this.password = password;
        this.loggedIn = false;
    }

    public void update(){
        // Does Savings Account Interest
        balance += balance * interest;
        balance = Math.round(balance *100.0)/100.0;

    }

    public double getInterest(){
        return interest;
    }

    /**
     * @throws IllegalArgumentException if interest invalid
     */
    public void setInterest(double interest){
        if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest Invalid: "+interest);
        }
        this.interest = interest;
    }

    /**
     * checks if interest is valid
     * @param interest possible valid interest
     * @return false if has more than 4 decimal points or <0. Otherwise true
     */
    public static boolean isInterestValid(double interest){
        if(interest<0){
            return false;
        }else if(interest != Math.round(interest*10000)/10000.0){
            return false;
        }
        return true;
    }
}
