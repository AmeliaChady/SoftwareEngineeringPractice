package edu.ithaca.dragon.bank;

import static edu.ithaca.dragon.bank.Utilities.isAmountValid;

public class CheckingAccount extends BankAccount{

    /**
     * @throws IllegalArgumentException if email or balance is invalid
     */
    public CheckingAccount(String email, double startingBalance){
        if (!isEmailValid(email)){
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        else if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance: " + startingBalance + " is invalid, cannot create account");
        }
        this.email = email;
        this.balance = startingBalance;
    }

    /**
     * Transfers some amount of money to another bank account
     * @param transferTo BankAccount to transfer to
     * @param amount amount to transfer
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative or has more than 2 decimals
     */
    public void transfer(CheckingAccount transferTo, double amount) throws InsufficientFundsException{
        if(!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid Amount");
        }else if (transferTo == this) {
            throw new IllegalArgumentException("Cannot transfer to self");
        }else if (transferTo == null) {
            throw new IllegalArgumentException("Cannot transfer to nothing");
        }else if(amount > balance){
            throw new InsufficientFundsException("Not Enough Funds");
        }
        this.balance -= amount;
        transferTo.balance += amount;

    }

    /**
     * Adds some amount of money to the account
     * @param amount amount to deposit
     * @throws IllegalArgumentException if amount has more than 2 decimals or is negative
     */
    public void deposit(double amount){
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Invalid Argument");
        }
        balance += amount;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws IllegalArgumentException when the amount is too large or negative
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Argument isn't non-negative with at max 2 decimal points");
        }
        //if amount trying to withdraw is greater than balance
        if (balance < amount){
            throw new InsufficientFundsException("ERROR: Not enough funds to withdraw that amount.");

        }
        balance -= amount;
    }

    public double getBalance(){
        return balance;
    }

    @Override
    public void update() {

    }
}


