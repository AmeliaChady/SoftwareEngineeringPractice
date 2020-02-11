package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {



    //---------------Kerry---------------//

    @Test
    public void fullDayTest(){
        AccountLibrary al = new AccountLibrary();
        BankAccount checkingAccount = new CheckingAccount("0000000001", 200.54);
        BankAccount savingsAccount = new SavingsAccount("0000000002", 1.00, 0.25);
        al.accounts.put(checkingAccount.getAccountID(), checkingAccount);
        al.accounts.put(savingsAccount.getAccountID(), savingsAccount);

        assertEquals(201.54, al.calcTotalAssets());


    }

    //---------------Amelia---------------//
    @Test
    public void suspiciousDaysTest() throws InsufficientFundsException{
        // Currently only considering Checking Accounts, as unable to get to Savings Accounts
        // Updates needed when possible

        CentralBank cb = new CentralBank();
        // Pretend Beforehand
        cb.createAccount("act0", 171);
        cb.createAccount("act1", 172);
        cb.createAccount("act2", 220);
        cb.createAccount("act3", 345);

        BasicAPI atm = cb;
        AdvancedAPI teller = cb;
        AdminAPI admin = cb;
        DaemonAPI daemon = cb;

        // Day 1

        teller.createAccount("act4", 210);
        teller.deposit("act2", 40);
        atm.transfer("act4", "act0", 10);
        atm.withdraw("act3", 100);
        teller.deposit("act1", 100);
        teller.transfer("act1", "act2", 20);
        atm.transfer("act4", "act0", 10);
        atm.withdraw("act3", 200);
        teller.checkBalance("act0");
        atm.transfer("act4", "act0", 10);
        admin.calcTotalAssets();
        atm.transfer("act4", "act0", 10);
        teller.deposit("act2", 10);
        atm.transfer("act4", "act0", 10);
        atm.transfer("act4", "act0", 10);

        daemon.accountUpdate();
        Collection<String> day1SuspiciousAccounts = admin.findAcctIdsWithSuspiciousActivity();
        admin.freezeAccount("act0");

        // Day 1 End Asserts

        Collection<String> day1CorrectSuspiciousAccounts = new LinkedList<String>();
        day1CorrectSuspiciousAccounts.add("act0");
        assertEquals(231, teller.checkBalance("act0"));
        assertEquals(252, teller.checkBalance("act1"));
        assertEquals(290, teller.checkBalance("act2"));
        assertEquals(45, teller.checkBalance("act3"));
        assertEquals(150, teller.checkBalance("act4"));
        assertEquals(day1CorrectSuspiciousAccounts, day1SuspiciousAccounts);

        // Day 2
        atm.deposit("act4", 10000);
        teller.withdraw("act1", 10);
        admin.calcTotalAssets();
        teller.withdraw("act3", 10);
        atm.transfer("act2", "act3", 10);

        daemon.accountUpdate();
        Collection<String> day2SuspiciousAccounts = admin.findAcctIdsWithSuspiciousActivity();
        admin.freezeAccount("act4");

        // Day 2 End Asserts

        Collection<String> day2CorrectSuspiciousAccounts = new LinkedList<String>();
        day2CorrectSuspiciousAccounts.add("act0");
        assertEquals(231, teller.checkBalance("act0"));
        assertEquals(242, teller.checkBalance("act1"));
        assertEquals(280, teller.checkBalance("act2"));
        assertEquals(45, teller.checkBalance("act3"));
        assertEquals(10150, teller.checkBalance("act4"));
        assertEquals(day2CorrectSuspiciousAccounts, day2SuspiciousAccounts);



    }

    //---------------Jolie---------------//
}
