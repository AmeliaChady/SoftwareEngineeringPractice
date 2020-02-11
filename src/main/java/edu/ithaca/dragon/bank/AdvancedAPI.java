package edu.ithaca.dragon.bank;

//API to be used by Teller systems
public interface AdvancedAPI extends BasicAPI {

    public void createCheckingAccount(String acctId, double startingBalance);

    public void createSavingsAccount(String acctId, double startingBalance, double interestRate);

    public void closeAccount(String acctId);
}
