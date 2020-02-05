package edu.ithaca.dragon.bank;

import static edu.ithaca.dragon.bank.Utilities.isAmountValid;

public class CheckingAccount extends BankAccount{

    /**
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    public CheckingAccount(String accountID, double startingBalance){
        if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance: " + startingBalance + " is invalid, cannot create account");
        }
        this.balance = startingBalance;
    }

    @Override
    public void update() {

    }
}


