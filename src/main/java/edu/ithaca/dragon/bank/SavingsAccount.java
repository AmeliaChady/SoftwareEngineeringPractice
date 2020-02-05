package edu.ithaca.dragon.bank;

public class SavingsAccount extends BankAccount{

    /**
     * Creates a savings account with daily interest
     * @throws IllegalArgumentException if accountID, interest or balance is invalid
     */
    SavingsAccount(String accountid, double startingBalance, double interest){

    }

    public void update(){
        // Does Savings Account Interest

    }

    public double getInterest(){
        return -1;
    }

    /**
     * @throws IllegalArgumentException if interest invalid
     */
    public void setInterest(double interest){

    }

    /**
     * checks if interest is valid
     * @param interest possible valid interest
     * @return false if has more than 4 decimal points or <0. Otherwise true
     */
    public static boolean isInterestValid(double interest){
        return false;
    }
}
