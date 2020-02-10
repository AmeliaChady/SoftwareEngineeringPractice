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

        BankAccount ba = new CheckingAccount("0000000000", 100);
        al.accounts.put("0000000000", ba);

        ba = new CheckingAccount("0000000001", 200);
        al.accounts.put("0000000001", ba);

        ba = new CheckingAccount("0000000002", 300);
        al.accounts.put("0000000002", ba);

        assertEquals(600, al.CalcTotalAssets());
    }

    @Test
    public void findSuspiciousAccountsTest() throws AccountFrozenException, InsufficientFundsException{
        AccountLibrary al = new AccountLibrary();

        BankAccount ba = new CheckingAccount("0000000000", 100);
        ba.deposit(100);
        ba.withdraw(100);
        al.accounts.put("0000000000", ba);

        // Too big - withdrawl
        ba = new CheckingAccount("0000000001", 10000000);
        ba.withdraw(1001);
        al.accounts.put("0000000001", ba);

        // Too big - deposit
        ba = new CheckingAccount("0000000002", 300);
        ba.deposit(1001);
        al.accounts.put("0000000002", ba);

        // Too many
        ba = new CheckingAccount("0000000003", 300);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        ba.withdraw(10);
        al.accounts.put("0000000003", ba);


        // Too big over smaller transactions
        ba = new CheckingAccount("0000000004", 300);
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
        BankAccount ba = new CheckingAccount("0000000000", 1);
        al.accounts.put("0000000000", ba);

        al.freezeAccount("0000000000");

        assertThrows(AccountFrozenException.class, () -> ba.deposit(10));
    }

    @Test
    public void unfreezeAccountTest(){
        AccountLibrary al = new AccountLibrary();
        BankAccount ba = new CheckingAccount("0123456789", 10);

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
        BankAccount ba = new SavingsAccount("0123456789", 1000, .1);
        al.accounts.put("0123456789", ba);

        al.updateAccounts();
        assertEquals(1100, ba.getBalance());
    }


}
