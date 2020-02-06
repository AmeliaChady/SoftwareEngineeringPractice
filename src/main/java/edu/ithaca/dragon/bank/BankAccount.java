package edu.ithaca.dragon.bank;

import java.util.List;

public abstract class BankAccount {

    protected String accountID;
    protected double balance;
    protected List<String> history;

    protected boolean accountFrozen;

    /**
     * Withdraws money from the account
     * @param amount amount to withdraw
     * @throws IllegalArgumentException if not a valid amount
     * @throws InsufficientFundsException if account doesn't hold enough money
     * @throws AccountFrozenException if account is frozen
     */
    public void withdraw(double amount) throws InsufficientFundsException, AccountFrozenException{
        // Please Keep First - Account Freeze has priority
        if (isAccountFrozen()){
            throw new AccountFrozenException("Account is frozen");
        }
    };

    /**
     * Deposits money into the account
     * @param amount amount to deposit
     * @throws IllegalArgumentException if not a valid amount
     * @throws AccountFrozenException if account is frozen
     */
    public void deposit(double amount) throws AccountFrozenException{
        // Please Keep First - Account Freeze has priority
        if(isAccountFrozen()){
            throw new AccountFrozenException("Account is frozen");
        }
    };

    /**
     * Transfers some amount of money to another bank account
     * @param transferTo the bank account to transfer to
     * @param amount the amount of money to transfer
     * @throws IllegalArgumentException if not a valid amount, transferTo is self, or transferto is null
     * @throws InsufficientFundsException if account doesn't hold enough money.
     * @throws AccountFrozenException if either account are frozen
     */
    public void transfer(BankAccount transferTo, double amount) throws InsufficientFundsException, AccountFrozenException{
        // Please Keep First - Account Freeze has priority
        if(isAccountFrozen()){
            throw new AccountFrozenException("Cannot transfer from frozen account");
        }else if(transferTo.isAccountFrozen()){
            throw new AccountFrozenException("Cannot transfer to frozen account");
        }
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

    /**
     * Freezes account, meaning all money removal and deposit functions
     * start throwing AccountFrozenException
     */
    public void freezeAccount(){
        accountFrozen = true;
    }

    /**
     * Unfreezes account, meaning all money removal and deposit functions
     * stop throwing AccountFrozenException
     */
    public void unfreezeAccount(){
        accountFrozen = false;
    }

    /**
     * @return account freeze status
     */
    public boolean isAccountFrozen(){
        return accountFrozen;
    }
}