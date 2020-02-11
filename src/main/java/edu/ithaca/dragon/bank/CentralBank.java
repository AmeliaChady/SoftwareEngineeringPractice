package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.List;

public class CentralBank implements AdvancedAPI, AdminAPI, DaemonAPI {
    AccountLibrary al;

    public CentralBank(){
        al = new AccountLibrary();
    }

    //----------------- BasicAPI methods -------------------------//

    public boolean confirmCredentials(String acctId, String password) {
        return false;
    }

    public double checkBalance(String acctId) {
        BankAccount ba = al.accounts.get(acctId);
        return ba.balance;
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, AccountFrozenException {
        BankAccount ba = al.accounts.get(acctId);
        ba.withdraw(amount);
    }

    public void deposit(String acctId, double amount) throws AccountFrozenException{
        BankAccount ba = al.accounts.get(acctId);
        ba.deposit(amount);
    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException, AccountFrozenException {
        BankAccount ba1 = al.accounts.get(acctIdToWithdrawFrom);
        BankAccount ba2 = al.accounts.get(acctIdToDepositTo);
        ba1.transfer(ba2, amount);
    }

    public List<Double> transactionHistory(String acctId) {
        BankAccount ba = al.accounts.get(acctId);
        return ba.getHistory();
    }


    //----------------- AdvancedAPI methods -------------------------//

    public void createAccount(String acctId, double startingBalance) {

    }

    public void closeAccount(String acctId) {

    }


    //------------------ AdminAPI methods -------------------------//

    public double calcTotalAssets() {
        return al.calcTotalAssets();
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity() {
        return al.findSuspiciousAccounts(1000, 10, 5000);
    }

    public void freezeAccount(String acctId) {
        al.freezeAccount(acctId);
    }

    public void unfreezeAcct(String acctId) {
        al.unfreezeAccount(acctId);
    }

    //----------------- DaemonAPI methods -------------------------//

    public void accountUpdate(){
        al.updateAccounts();
    }

}
