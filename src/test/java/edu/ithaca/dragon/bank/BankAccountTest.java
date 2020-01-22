package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        // Prefix Tests
        assertFalse(BankAccount.isEmailValid("xxx-@yyyy.com"));
        assertFalse(BankAccount.isEmailValid("xx..xxx@yyyy.com"));
        assertFalse(BankAccount.isEmailValid(".xxx@yyyy.com"));
        assertFalse(BankAccount.isEmailValid("xxx#xxx@yyyy.com"));

        assertTrue(BankAccount.isEmailValid("xxx-x@yyyy.com"));
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.com"));
        assertTrue(BankAccount.isEmailValid("xxx@yyyy.com"));
        assertTrue(BankAccount.isEmailValid("xxx_xxx@yyyy.com"));

        // Suffix Tests
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy.c"));
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy#yyyyyyy.com"));
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy"));
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy..com"));

        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.cc"));
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy-yyyyyyy.com"));
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.org"));
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.com"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}