package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccountLibraryTests {

    @Test
    public void constructorTest(){
        try{
            new AccountLibrary();
        }catch (Exception e){
            fail("throws an exception");
        }

        AccountLibrary al = new AccountLibrary();
        assertNotNull(al.accounts);
    }

    @Test
    public void calcTotalAssetsTest(){
        AccountLibrary al = new AccountLibrary();

        BankAccount ba = new CheckingAccount("0000000000", 100, "password!1");
        al.accounts.put("0000000000", ba);

        ba = new CheckingAccount("0000000001", 200, "password!1");
        al.accounts.put("0000000001", ba);

        ba = new CheckingAccount("0000000002", 300, "password!1");
        al.accounts.put("0000000002", ba);

        assertEquals(600, al.calcTotalAssets());
    }

    @Test
    public void findSuspiciousAccountsTest() throws AccountFrozenException, InsufficientFundsException{
        AccountLibrary al = new AccountLibrary();

        BankAccount ba = new CheckingAccount("0000000000", 100, "password!1");
        ba.confirmCredentials("password!1");
        ba.deposit(100);
        ba.withdraw(100);
        al.accounts.put("0000000000", ba);

        // Too big - withdrawl
        ba = new CheckingAccount("0000000001", 10000000, "password!1");
        ba.confirmCredentials("password!1");
        ba.withdraw(1001);
        al.accounts.put("0000000001", ba);

        // Too big - deposit
        ba = new CheckingAccount("0000000002", 300, "password!1");
        ba.confirmCredentials("password!1");
        ba.deposit(1001);
        al.accounts.put("0000000002", ba);

        // Too many
        ba = new CheckingAccount("0000000003", 300, "password!1");
        ba.confirmCredentials("password!1");
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        al.accounts.put("0000000003", ba);


        // Too big over smaller transactions
        ba = new CheckingAccount("0000000004", 300, "password!1");
        ba.confirmCredentials("password!1");
        ba.deposit(201);
        ba.deposit(201);
        ba.deposit(201);
        ba.deposit(201);
        ba.deposit(201);
        al.accounts.put("0000000004", ba);

        Collection<String> suspicious = new LinkedList<>();
        suspicious.add("0000000001");
        suspicious.add("0000000002");
        suspicious.add("0000000003");
        suspicious.add("0000000004");

        Collection<String> alfind = al.findSuspiciousAccounts(1000, 5, 1004);
        assertEquals(suspicious, alfind);

    }

    @Test
    public void freezeAccountTest(){
        AccountLibrary al = new AccountLibrary();
        BankAccount ba = new CheckingAccount("0000000000", 1, "password!1");
        al.accounts.put("0000000000", ba);

        al.freezeAccount("0000000000");

        assertThrows(AccountFrozenException.class, () -> ba.deposit(10));
    }

    @Test
    public void unfreezeAccountTest() throws AccountFrozenException{
        AccountLibrary al = new AccountLibrary();
        BankAccount ba = new CheckingAccount("0123456789", 10, "password!1");
        ba.confirmCredentials("password!1");
        ba.freezeAccount();
        al.accounts.put("0123456789", ba);
        al.unfreezeAccount("0123456789");
        ba.confirmCredentials("password!1");
        try{
            ba.deposit(10);
        }catch (AccountFrozenException a){
            fail("Still frozen");
        }
    }

    @Test
    public void updateAccounts(){
        AccountLibrary al = new AccountLibrary();
        BankAccount ba = new SavingsAccount("0123456789", 1000, "password!1", .1);
        al.accounts.put("0123456789", ba);

        al.updateAccounts();
        assertEquals(1100, ba.getBalance());
    }


    @Test
    public void getBalanceTest() {
        AccountLibrary al = new AccountLibrary();

        BankAccount ba1 = new CheckingAccount("0000000000", 100, "password!1");
        al.accounts.put("0000000000", ba1);
        assertEquals(100, al.getBalance("0000000000"));

        BankAccount ba2 = new SavingsAccount("0000000001", 100, "password!1", 0.1);
        al.accounts.put("0000000001", ba2);
        assertEquals(100, al.getBalance("0000000000"));
    }

    @Test
    public void createAccountTest(){
        AccountLibrary al = new AccountLibrary();
        Map<String, String> args;

        // Checking
        args = CheckingAccount.makeCheckingMap("0000000001", 100, "password!1");
        al.createAccount(args);
        assertNotNull(al.accounts.get("0000000001"));
        Map<String, String> argsCFail = CheckingAccount.makeCheckingMap("0000000001", -1,"password!1");
        assertThrows(IllegalArgumentException.class, () -> al.createAccount(argsCFail));


        // Savings
        args = SavingsAccount.makeSavingsMap("0000000002", 100, "password!2", 10);
        al.createAccount(args);
        assertNotNull(al.accounts.get("0000000002"));
        Map<String, String> argsSFail = SavingsAccount.makeSavingsMap("0000000002", 100, "password!2", -5);
        assertThrows(IllegalArgumentException.class, () -> al.createAccount(argsSFail));

        // No reusing accountID
        Map<String,String> argsIDFail = SavingsAccount.makeSavingsMap("0000000002", 100, "password!2", 10);
        assertThrows(IllegalArgumentException.class, () ->al.createAccount(argsIDFail));
    }

    @Test
    public void createCheckingAccountTest() {
        AccountLibrary al = new AccountLibrary();

        al.createCheckingAccount("0000000000", 100, "password!1");
        assertNotNull(al.accounts.get("0000000000"));

        BankAccount ba1 = new CheckingAccount("0000000000", 100, "password!1");
        al.accounts.put("0000000000", ba1);
        assertNotNull(al.accounts.get("0000000000"));
    }

    @Test
    public void createSavingsAccountTest() {
        AccountLibrary al = new AccountLibrary();
        al.createSavingsAccount("0000000000", 100, "password!1", 0.1);
        assertNotNull(al.accounts.get("0000000000"));
    }

    @Test
    public void closeAccountTest() {
        AccountLibrary al = new AccountLibrary();
        BankAccount ba1 = new CheckingAccount("0000000000", 100, "password!1");
        BankAccount ba2 = new SavingsAccount("0000000001", 100, "password!1", 0.1);
        al.closeAccount("0000000000");
        al.closeAccount("0000000001");
        assertNull(al.accounts.get("0000000000"));
        assertNull(al.accounts.get("0000000001"));
    }



}
