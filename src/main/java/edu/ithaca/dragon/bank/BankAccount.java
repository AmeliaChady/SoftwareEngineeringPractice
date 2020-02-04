package edu.ithaca.dragon.bank;

import java.util.List;

public abstract class BankAccount {

    protected String accountID;
    protected double balance;
    protected List<String> history;

    /**
     *
     * @param amount
     * @throws InsufficientFundsException
     */
    public void withdraw(double amount) throws InsufficientFundsException{

    };

    public void deposit(double amount){

    };

    public void transfer(BankAccount transferTo, double amount) throws InsufficientFundsException{

    };

    abstract public void update();

    public String getAccountID(){
        return accountID;
    };
    public double getBalance(){
        return -1;
    };
    public List<Double> getHistory(){
        return null;
    };
}