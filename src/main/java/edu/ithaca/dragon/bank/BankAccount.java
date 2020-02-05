package edu.ithaca.dragon.bank;

import java.util.List;

public abstract class BankAccount {

    protected String accountID;
    protected double balance;
    protected List<String> history;

    /**
     * Withdraws money from the account
     * @param amount amount to withdraw
     * @throws IllegalArgumentException if not a valid amount
     * @throws InsufficientFundsException if account doesn't hold enough money
     */
    public void withdraw(double amount) throws InsufficientFundsException{

    };

    /**
     * Deposits money into the account
     * @param amount amount to deposit
     * @throws IllegalArgumentException if not a valid amount
     */
    public void deposit(double amount){

    };

    /**
     * Transfers some amount of money to another bank account
     * @param transferTo the bank account to transfer to
     * @param amount the amount of money to transfer
     * @throws IllegalArgumentException if not a valid amount, transferTo is self, or transferto is null
     * @throws InsufficientFundsException if account doesn't hold enough money.
     */
    public void transfer(BankAccount transferTo, double amount) throws InsufficientFundsException{

    };

    /**
     * Does any updating that needs to happen on some basis
     */
    abstract public void update();

    public String getAccountID(){
        return accountID;
    };
    public double getBalance(){
        return balance;
    };

    /**
     * @return A read-only version of getHistory
     */
    public List<Double> getHistory(){
        return null;
    };
}