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
        return 0;
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException {

    }

    public void deposit(String acctId, double amount) {

    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws InsufficientFundsException {

    }

    public List<Double> transactionHistory(String acctId) {
        return null;
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
