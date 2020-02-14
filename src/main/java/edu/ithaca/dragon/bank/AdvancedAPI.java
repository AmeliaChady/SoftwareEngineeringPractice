package edu.ithaca.dragon.bank;

//API to be used by Teller systems
public interface AdvancedAPI extends BasicAPI {

    public void createCheckingAccount(String acctId, double startingBalance, String password);

    public void createSavingsAccount(String acctId, double startingBalance, String password, double interestRate);

    public void createLoanAccount(String acctId, double startingBalance, String password, double interestRate);

    public void closeAccount(String acctId);
}
