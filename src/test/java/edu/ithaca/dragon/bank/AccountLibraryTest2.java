package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountLibraryTest2 {

    @Test
    public void getBalanceTest() {
        AccountLibrary al = new AccountLibrary();

        BankAccount ba1 = new CheckingAccount("0000000000", 100);
        al.accounts.put("0000000000", ba1);
        assertEquals(100, al.getBalance("0000000000"));

        BankAccount ba2 = new SavingsAccount("0000000001", 100, 0.1);
        al.accounts.put("0000000001", ba2);
        assertEquals(100, al.getBalance("0000000000"));
    }

    @Test
    public void createCheckingAccountTest() {
        AccountLibrary al = new AccountLibrary();

        BankAccount ba1 = new CheckingAccount("0000000000", 100);
        assertNotNull(al.accounts.get(0000000000));
    }

    @Test
    public void createSavingsAccountTest() {
    }

    @Test
    public void closeAccountTest() {
    }


}

