package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

        BankAccount ba = new CheckingAccount("0000000000", 100, "password");
        al.accounts.put("0000000000", ba);

        ba = new CheckingAccount("0000000001", 200, "password");
        al.accounts.put("0000000001", ba);

        ba = new CheckingAccount("0000000002", 300, "password");
        al.accounts.put("0000000002", ba);

        assertEquals(600, al.calcTotalAssets());
    }

    @Test
    public void findSuspiciousAccountsTest() throws AccountFrozenException, InsufficientFundsException{
        AccountLibrary al = new AccountLibrary();

        BankAccount ba = new CheckingAccount("0000000000", 100, "password");
        ba.deposit(100);
        ba.withdraw(100);
        al.accounts.put("0000000000", ba);

        // Too big - withdrawl
        ba = new CheckingAccount("0000000001", 10000000, "password");
        ba.withdraw(1001);
        al.accounts.put("0000000001", ba);

        // Too big - deposit
        ba = new CheckingAccount("0000000002", 300, "password");
        ba.deposit(1001);
        al.accounts.put("0000000002", ba);

        // Too many
        ba = new CheckingAccount("0000000003", 300, "password");
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        al.accounts.put("0000000003", ba);


        // Too big over smaller transactions
        ba = new CheckingAccount("0000000004", 300, "password");
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
        BankAccount ba = new CheckingAccount("0000000000", 1, "password");
        al.accounts.put("0000000000", ba);

        al.freezeAccount("0000000000");

        assertThrows(AccountFrozenException.class, () -> ba.deposit(10));
    }

    @Test
    public void unfreezeAccountTest(){
        AccountLibrary al = new AccountLibrary();
        BankAccount ba = new CheckingAccount("0123456789", 10, "password");

        ba.freezeAccount();
        al.accounts.put("0123456789", ba);
        al.unfreezeAccount("0123456789");

        try{
            ba.deposit(10);
        }catch (AccountFrozenException a){
            fail("Still frozen");
        }
    }

    @Test
    public void updateAccounts(){
        AccountLibrary al = new AccountLibrary();
        BankAccount ba = new SavingsAccount("0123456789", 1000, "password", .1);
        al.accounts.put("0123456789", ba);

        al.updateAccounts();
        assertEquals(1100, ba.getBalance());
    }


    @Test
    public void getBalanceTest() {
        AccountLibrary al = new AccountLibrary();

        BankAccount ba1 = new CheckingAccount("0000000000", 100, "password");
        al.accounts.put("0000000000", ba1);
        assertEquals(100, al.getBalance("0000000000"));

        BankAccount ba2 = new SavingsAccount("0000000001", 100, "password", 0.1);
        al.accounts.put("0000000001", ba2);
        assertEquals(100, al.getBalance("0000000000"));
    }

    @Test
    public void createCheckingAccountTest() {
        AccountLibrary al = new AccountLibrary();

        al.createCheckingAccount("0000000000", 100, "password");
        assertNotNull(al.accounts.get("0000000000"));

        BankAccount ba1 = new CheckingAccount("0000000000", 100, "password");
        al.accounts.put("0000000000", ba1);
        assertNotNull(al.accounts.get("0000000000"));
    }


    @Test
    public void createSavingsAccountTest() {
        AccountLibrary al = new AccountLibrary();
        al.createSavingsAccount("0000000000", 100, "password", 0.1);
        assertNotNull(al.accounts.get("0000000000"));
    }

    @Test
    public void closeAccountTest() {
        AccountLibrary al = new AccountLibrary();
        BankAccount ba1 = new CheckingAccount("0000000000", 100, "password");
        BankAccount ba2 = new SavingsAccount("0000000001", 100, "password", 0.1);
        al.closeAccount("0000000000");
        al.closeAccount("0000000001");
        assertNull(al.accounts.get("0000000000"));
        assertNull(al.accounts.get("0000000001"));
    }



}
