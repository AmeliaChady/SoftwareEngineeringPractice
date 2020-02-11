package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {



    //---------------Kerry---------------//


    //---------------Amelia---------------//
    @Test
    public void suspiciousDaysTest() throws InsufficientFundsException{
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
        atm.withdraw("act3", 300);
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
        assertEquals(, teller.checkBalance("act0"));
        assertEquals(, teller.checkBalance("act1"));
        assertEquals(, teller.checkBalance("act2"));
        assertEquals(, teller.checkBalance("act3"));
        assertEquals(, teller.checkBalance("act4"));
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
        assertEquals(, teller.checkBalance("act0"));
        assertEquals(, teller.checkBalance("act1"));
        assertEquals(, teller.checkBalance("act2"));
        assertEquals(, teller.checkBalance("act3"));
        assertEquals(, teller.checkBalance("act4"));
        assertEquals(day2CorrectSuspiciousAccounts, day2SuspiciousAccounts);



    }

    //---------------Jolie---------------//
}
