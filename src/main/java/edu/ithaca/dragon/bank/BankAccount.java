package edu.ithaca.dragon.bank;

import java.util.Collections;
import java.util.List;

public abstract class BankAccount {

    protected String accountID;
    protected double balance;
    protected List<Double> history;

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
        if(!Utilities.isAmountValid(amount)){
            throw new IllegalArgumentException("ERROR: invalid amount");
        }
        if(amount > balance){
            throw new InsufficientFundsException("ERROR: You do not have enough funds to withdraw that amount.");
        }
        balance -= amount;
        balance = Math.round(balance *100.0)/100.0;
        updateHistory(amount, false);
    }

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
        if(!Utilities.isAmountValid(amount)){
            throw new IllegalArgumentException("ERROR: invalid amount");
        }
        balance += amount;
        balance = Math.round(balance *100.0)/100.0;
        updateHistory(amount, true);
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
        //a bank account must not be null in order to check if it's frozen or not (see below comment)
        if (transferTo == null){
            throw new IllegalArgumentException("ERROR: Must pass bank account");
        }

        // Please Keep First - Account Freeze has priority
        if(isAccountFrozen()){
            throw new AccountFrozenException("Cannot transfer from frozen account");
        }else if(transferTo.isAccountFrozen()){
            throw new AccountFrozenException("Cannot transfer to frozen account");
        }

        if(balance < amount){
            throw new InsufficientFundsException("ERROR: Not enough funds");
        }
        if (!Utilities.isAmountValid(amount)){
            throw new IllegalArgumentException("ERROR: Invalid amount");
        }
        if (transferTo == this){
            throw new IllegalArgumentException("ERROR: Must pass two bank accounts");
        }
        withdraw(amount);
        transferTo.deposit(amount);

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
        List<Double> historyReturn = Collections.unmodifiableList(history);
        return historyReturn;
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

    public void updateHistory(Double amount, boolean isDeposit) { //true means deposit, false means withdraw
        if(!Utilities.isAmountValid(amount)){
            throw new IllegalArgumentException("ERROR: invalid amount");
        }
        if (isDeposit){
            history.add(amount);
        }
        else{
            history.add(-1 * amount);
        }
    }
}