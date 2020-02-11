package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {



    //---------------Kerry---------------//

    @Test
    public void fullDayTest() throws InsufficientFundsException{

        //account creation
        CentralBank centralBank = new CentralBank();
        centralBank.createAccount("123", 190);
        centralBank.createAccount("456", 230.60);
        centralBank.createAccount("789", 509.23);

        //money transfer
        centralBank.deposit("123", 10);
        centralBank.transfer("456", "789", 30.60);
        centralBank.withdraw("789", 39.83);

        //assertions
        assertEquals(200, centralBank.checkBalance("123"));
        assertEquals(200, centralBank.checkBalance("456"));
        assertEquals(500, centralBank.checkBalance("789"));
        assertEquals(1, centralBank.transactionHistory("123"));
        assertEquals(1, centralBank.transactionHistory("456"));
        assertEquals(2, centralBank.transactionHistory("789"));
        assertEquals(800, centralBank.calcTotalAssets());

        //money transfer
        centralBank.withdraw("123", 50);
        centralBank.transfer("789", "123", 100);
        centralBank.deposit("456", 50.99);

        //assertions
        assertEquals(250, centralBank.checkBalance("123"));
        assertEquals(250.99, centralBank.checkBalance("456"));
        assertEquals(400, centralBank.checkBalance("789"));
        assertEquals(3, centralBank.transactionHistory("123").size());
        assertEquals(2, centralBank.transactionHistory("456").size());
        assertEquals(3, centralBank.transactionHistory("789").size());
        assertEquals(900.99, centralBank.calcTotalAssets());

        //freeze account
        centralBank.freezeAccount("123");
        assertThrows(AccountFrozenException.class, () -> centralBank.deposit("123", 50));
        assertEquals(250, centralBank.checkBalance("123"));
        assertThrows(AccountFrozenException.class, () -> centralBank.withdraw("123", 50));
        assertEquals(250, centralBank.checkBalance("123"));
        assertEquals(3, centralBank.transactionHistory("123").size());

        //unfreeze
        centralBank.unfreezeAcct("123");
        centralBank.deposit("123", 10);
        assertEquals(260, centralBank.checkBalance("123"));
        centralBank.withdraw("123", 10);
        assertEquals(250, centralBank.checkBalance("123"));


        assertEquals(0, centralBank.findAcctIdsWithSuspiciousActivity().size());
        centralBank.deposit("123", 100000);
        centralBank.withdraw("123", 100000);
        assertEquals(1, centralBank.findAcctIdsWithSuspiciousActivity().size());



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
