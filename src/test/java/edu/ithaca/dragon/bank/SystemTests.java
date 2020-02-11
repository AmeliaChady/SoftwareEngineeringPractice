package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {



    //---------------Kerry---------------//

    @Test
    public void fullDayTest() throws InsufficientFundsException, AccountFrozenException{

        //account creation
        CentralBank centralBank = new CentralBank();
        centralBank.createCheckingAccount("1234567890", 190);
        centralBank.createCheckingAccount("4567890123", 230.60);
        centralBank.createCheckingAccount("7890123456", 509.23);

        //money transfer
        centralBank.deposit("1234567890", 10);
        centralBank.transfer("4567890123", "7890123456", 30.60);
        centralBank.withdraw("7890123456", 39.83);

        //assertions
        assertEquals(200, centralBank.checkBalance("1234567890"));
        assertEquals(200, centralBank.checkBalance("4567890123"));
        assertEquals(500, centralBank.checkBalance("7890123456"));
        assertEquals(1, centralBank.transactionHistory("1234567890").size());
        assertEquals(1, centralBank.transactionHistory("4567890123").size());
        assertEquals(2, centralBank.transactionHistory("7890123456").size());
        assertEquals(900, centralBank.calcTotalAssets());

        //money transfer
        centralBank.withdraw("1234567890", 50);
        centralBank.transfer("7890123456", "1234567890", 100);
        centralBank.deposit("4567890123", 50.99);

        //assertions
        assertEquals(250, centralBank.checkBalance("1234567890"));
        assertEquals(250.99, centralBank.checkBalance("4567890123"));
        assertEquals(400, centralBank.checkBalance("7890123456"));
        assertEquals(3, centralBank.transactionHistory("1234567890").size());
        assertEquals(2, centralBank.transactionHistory("4567890123").size());
        assertEquals(3, centralBank.transactionHistory("7890123456").size());
        assertEquals(900.99, centralBank.calcTotalAssets());

        //freeze account
        centralBank.freezeAccount("1234567890");
        assertThrows(AccountFrozenException.class, () -> centralBank.deposit("1234567890", 50));
        assertEquals(250, centralBank.checkBalance("1234567890"));
        assertThrows(AccountFrozenException.class, () -> centralBank.withdraw("1234567890", 50));
        assertEquals(250, centralBank.checkBalance("1234567890"));
        assertEquals(3, centralBank.transactionHistory("1234567890").size());

        //unfreeze
        centralBank.unfreezeAcct("1234567890");
        centralBank.deposit("1234567890", 10);
        assertEquals(260, centralBank.checkBalance("1234567890"));
        centralBank.withdraw("1234567890", 10);
        assertEquals(250, centralBank.checkBalance("1234567890"));


        assertEquals(0, centralBank.findAcctIdsWithSuspiciousActivity().size());
        centralBank.deposit("1234567890", 100000);
        centralBank.withdraw("1234567890", 100000);
        assertEquals(1, centralBank.findAcctIdsWithSuspiciousActivity().size());



    }

    //---------------Amelia---------------//
    @Test
    public void suspiciousDaysTest() throws InsufficientFundsException, AccountFrozenException{
        // Currently only considering Checking Accounts, as unable to get to Savings Accounts
        // Updates needed when possible

        CentralBank cb = new CentralBank();
        // Pretend Beforehand
        cb.createCheckingAccount("4528177224", 171);
        cb.createCheckingAccount("5712345456", 172);
        cb.createCheckingAccount("2222222222", 220);
        cb.createCheckingAccount("7824247312", 345);

        BasicAPI atm = cb;
        AdvancedAPI teller = cb;
        AdminAPI admin = cb;
        DaemonAPI daemon = cb;

        // Day 1

        teller.createCheckingAccount("9182355567", 210);
        teller.deposit("2222222222", 40);
        atm.transfer("9182355567", "4528177224", 10);
        atm.withdraw("7824247312", 100);
        teller.deposit("5712345456", 100);
        teller.transfer("5712345456", "2222222222", 20);
        atm.transfer("9182355567", "4528177224", 10);
        atm.withdraw("7824247312", 200);
        teller.checkBalance("4528177224");
        atm.transfer("9182355567", "4528177224", 10);
        admin.calcTotalAssets();
        atm.transfer("9182355567", "4528177224", 10);
        teller.deposit("2222222222", 10);
        atm.transfer("9182355567", "4528177224", 10);
        atm.transfer("9182355567", "4528177224", 10);

        daemon.accountUpdate();
        Collection<String> day1SuspiciousAccounts = admin.findAcctIdsWithSuspiciousActivity();
        admin.freezeAccount("4528177224");

        // Day 1 End Asserts

        Collection<String> day1CorrectSuspiciousAccounts = new LinkedList<String>();
        day1CorrectSuspiciousAccounts.add("4528177224");
        assertEquals(231, teller.checkBalance("4528177224"));
        assertEquals(252, teller.checkBalance("5712345456"));
        assertEquals(290, teller.checkBalance("2222222222"));
        assertEquals(45, teller.checkBalance("7824247312"));
        assertEquals(150, teller.checkBalance("9182355567"));
        assertEquals(day1CorrectSuspiciousAccounts, day1SuspiciousAccounts);

        // Day 2
        atm.deposit("9182355567", 10000);
        teller.withdraw("5712345456", 10);
        admin.calcTotalAssets();
        teller.withdraw("7824247312", 10);
        atm.transfer("2222222222", "7824247312", 10);

        daemon.accountUpdate();
        Collection<String> day2SuspiciousAccounts = admin.findAcctIdsWithSuspiciousActivity();
        admin.freezeAccount("9182355567");

        // Day 2 End Asserts

        Collection<String> day2CorrectSuspiciousAccounts = new LinkedList<String>();
        day2CorrectSuspiciousAccounts.add("4528177224");
        assertEquals(231, teller.checkBalance("4528177224"));
        assertEquals(242, teller.checkBalance("5712345456"));
        assertEquals(280, teller.checkBalance("2222222222"));
        assertEquals(45, teller.checkBalance("7824247312"));
        assertEquals(10150, teller.checkBalance("9182355567"));
        assertEquals(day2CorrectSuspiciousAccounts, day2SuspiciousAccounts);



    }

    //---------------Jolie---------------//

    @Test
    public void fullDaySavingsTest() throws InsufficientFundsException, AccountFrozenException{

    }
}
