package edu.ithaca.dragon.bank;

import java.util.List;

//API to be used by ATMs
public interface BasicAPI {

    boolean confirmCredentials(String acctId, String password) throws AccountFrozenException;

    double checkBalance(String acctId);

    void withdraw(String acctId, double amount) throws InsufficientFundsException, AccountFrozenException;

    void deposit(String acctId, double amount) throws AccountFrozenException;

    void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException, AccountFrozenException;

    List<Double> transactionHistory(String acctId);

}
