package edu.ithaca.dragon.bank;

import static edu.ithaca.dragon.bank.Utilities.*;

public class SavingsAccount extends BankAccount{
    double interest;

    /**
     * Creates a savings account with daily interest
     * @throws IllegalArgumentException if accountID, interest or balance is invalid
     */
    SavingsAccount(String accountid, double startingBalance, double interest){
        if(!isAccountIDValid(accountid)){
            throw new IllegalArgumentException("Account ID invalid");
        }else if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance invalid");
        }else if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest invalid");
        }

        this.accountID = accountid;
        this.balance = startingBalance;
        this.interest = interest;
    }

    public void update(){
        // Does Savings Account Interest
        balance += balance * interest;

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
