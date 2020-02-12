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
        centralBank.createCheckingAccount("1234567890", 190, "password!1");
        centralBank.createCheckingAccount("4567890123", 230.60, "password!1");
        centralBank.createCheckingAccount("7890123456", 509.23, "password!1");

        //account login
        centralBank.confirmCredentials("1234567890", "password!1");
        centralBank.confirmCredentials("4567890123", "password!1");
        centralBank.confirmCredentials("7890123456", "password!1");

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
        centralBank.confirmCredentials("1234567890", "password!1");
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
        cb.createCheckingAccount("4528177224", 171, "password!1");
        cb.createCheckingAccount("5712345456", 172, "password!1");
        cb.createCheckingAccount("2222222222", 220, "password!1");
        cb.createCheckingAccount("7824247312", 345, "password!1");

        //login
        cb.confirmCredentials("4528177224", "password!1");
        cb.confirmCredentials("5712345456", "password!1");
        cb.confirmCredentials("2222222222", "password!1");
        cb.confirmCredentials("7824247312", "password!1");

        BasicAPI atm = cb;
        AdvancedAPI teller = cb;
        AdminAPI admin = cb;
        DaemonAPI daemon = cb;

        // Day 1

        teller.createCheckingAccount("9182355567", 210, "password!1");
        teller.confirmCredentials("9182355567","password!1");
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
        atm.transfer("9182355567", "4528177224", 10);
        atm.transfer("9182355567", "4528177224", 10);
        atm.transfer("9182355567", "4528177224", 10);
        atm.transfer("9182355567", "4528177224", 10);

        daemon.accountUpdate();
        Collection<String> day1SuspiciousAccounts = admin.findAcctIdsWithSuspiciousActivity();
        admin.freezeAccount("4528177224");

        // Day 1 End Asserts

        Collection<String> day1CorrectSuspiciousAccounts = new LinkedList<String>();
        day1CorrectSuspiciousAccounts.add("4528177224");
        day1CorrectSuspiciousAccounts.add("9182355567");
        assertEquals(271, teller.checkBalance("4528177224"));
        assertEquals(252, teller.checkBalance("5712345456"));
        assertEquals(290, teller.checkBalance("2222222222"));
        assertEquals(45, teller.checkBalance("7824247312"));
        assertEquals(110, teller.checkBalance("9182355567"));
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
        day2CorrectSuspiciousAccounts.add("9182355567");
        assertEquals(271, teller.checkBalance("4528177224"));
        assertEquals(242, teller.checkBalance("5712345456"));
        assertEquals(280, teller.checkBalance("2222222222"));
        assertEquals(45, teller.checkBalance("7824247312"));
        assertEquals(10110, teller.checkBalance("9182355567"));
        assertEquals(day2CorrectSuspiciousAccounts, day2SuspiciousAccounts);



    }

    //---------------Jolie---------------//

    @Test
    public void fullDaySavingsTest() throws InsufficientFundsException, AccountFrozenException{
        CentralBank cBank = new CentralBank();

        cBank.createSavingsAccount("0000000000", 100, "password!1", 0.1);
        cBank.createSavingsAccount("1234567890", 500.50, "password!1", 0.05);
        cBank.createSavingsAccount("0987654321", 1000, "password!1", .2);

        //login
        cBank.confirmCredentials("0000000000","password!1");
        cBank.confirmCredentials("1234567890", "password!1");
        cBank.confirmCredentials("0987654321","password!1");

        BasicAPI atm = cBank;
        AdvancedAPI teller = cBank;
        AdminAPI central = cBank;
        DaemonAPI auto = cBank;

        //day 1
        teller.createSavingsAccount("1111111111", 400, "password!1", .25);
        teller.confirmCredentials("1111111111", "password!1");
        teller.deposit("1111111111", 100);
        teller.withdraw("1234567890", 100);
        teller.transfer("0987654321", "1111111111", 50);
        assertEquals(100, teller.checkBalance("0000000000"));
        assertEquals(400.50, teller.checkBalance("1234567890"));
        assertEquals(950, teller.checkBalance("0987654321"));
        assertEquals(550, teller.checkBalance("1111111111"));
        atm.transfer("1111111111", "1234567890", 100);
        atm.withdraw("0000000000", 100);
        atm.deposit("0000000000", 300);
        assertEquals(300, teller.checkBalance("0000000000"));
        assertEquals(500.50, teller.checkBalance("1234567890"));
        assertEquals(950, teller.checkBalance("0987654321"));
        assertEquals(450, teller.checkBalance("1111111111"));

        //update
        auto.accountUpdate();

        //interest update check
        assertEquals(330, teller.checkBalance("0000000000"));
        assertEquals(525.53, teller.checkBalance("1234567890"));
        assertEquals(1140, teller.checkBalance("0987654321"));
        assertEquals(562.5, teller.checkBalance("1111111111"));

        //close account check
        teller.closeAccount("1234567890");
        assertNull(cBank.al.accounts.get("1234567890"));

    }
}
