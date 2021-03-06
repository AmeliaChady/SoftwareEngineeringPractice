package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CentralBank implements AdvancedAPI, AdminAPI, DaemonAPI {
    AccountLibrary al;

    public CentralBank(){
        al = new AccountLibrary();
    }

    //----------------- BasicAPI methods -------------------------//

    public boolean confirmCredentials(String acctId, String password) throws AccountFrozenException{
        return al.confirmCredentials(acctId, password);
    }

    public double checkBalance(String acctId) {
        return al.getBalance(acctId);
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, AccountFrozenException {
        al.withdraw(acctId, amount);
    }

    public void deposit(String acctId, double amount) throws AccountFrozenException{
        al.deposit(acctId, amount);
    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException, AccountFrozenException {
        al.transfer(acctIdToWithdrawFrom, acctIdToDepositTo, amount);
    }

    public List<Double> transactionHistory(String acctId) {
        return al.transactionHistory(acctId);
    }


    //----------------- AdvancedAPI methods -------------------------//
    public void createAccount(Map<String, String> args){
        al.createAccount(args);
    }

    @Deprecated
    public void createCheckingAccount(String acctId, double startingBalance, String password) {
        al.createCheckingAccount(acctId, startingBalance, password);
    }

    @Deprecated
    public void createSavingsAccount(String acctId, double startingBalance, String password, double interestRate) {
        al.createSavingsAccount(acctId, startingBalance, password, interestRate);
    }

    public void closeAccount(String acctId) {
        al.closeAccount(acctId);
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

    public static CentralBank testBank(){
        CentralBank cb = new CentralBank();
        cb.createAccount(SavingsAccount.makeSavingsMap(
                "0011223344",
                100,
                "!n0Thinking",
                .05));
        cb.createAccount(CheckingAccount.makeCheckingMap(
                "0123456789",
                100,
                "Sparkling@3"));
        cb.createAccount(CheckingAccount.makeCheckingMap(
                "0102030405",
                100,
                "3atPassword!"
        ));
        cb.freezeAccount("0102030405");
        return cb;
    }

}
