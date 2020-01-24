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

        // Normal Withdrawl
        //this is an equivalent class with zero withdraw that produces the same result
        //this withdrawl is within the border of allowed transactions
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());

        // Zero Withdrawl
        //this is an equivalent class with normal withdraw that produces the same result
        //this withdrawl is within the border of allowed transactions
        bankAccount.withdraw(0);
        assertEquals(100, bankAccount.getBalance());

        // Negative Withdrawl
        //this withdrawl amount is right outside the border of allowed numbers
        //there is no equivalence class for this test
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-1));

        // Overdraw
        //this withdrawl is far outside of the border of allowed withdrawls for current account balance
        //there is no equivalence class for this test
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(5000));

        //an equivalence class for negative amount transaction does not exist
        //there is no border case for withdrawing right outside the balance (201)


    }

    @Test
    void isEmailValidTest(){
        // Prefix Tests

        //this is a border test, as it only has one "-" before the "@"
        //this is not in a equivalence class
        assertFalse(BankAccount.isEmailValid("xxx-@yyyy.com"));

        //this is in an equivalence class with the ".." in the suffix test
        //this is a border class, as it tests right outside the allowed "." limit
        assertFalse(BankAccount.isEmailValid("xx..xxx@yyyy.com"));

        //not a border class
        //no equivalent class
        assertFalse(BankAccount.isEmailValid(".xxx@yyyy.com"));

        //not a border class, as there is no allowed range of "#" character
        //equivalent class is when the same thing is tested in suffix
        assertFalse(BankAccount.isEmailValid("xxx#xxx@yyyy.com"));

        //not a border class
        //equivalent class with the test below (one instance of a character)
        assertTrue(BankAccount.isEmailValid("xxx-x@yyyy.com"));
        //not a border class
        //equivalent class with the test below (one instance of a character)
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.com"));
        //not a border class
        //no equivalence test
        assertTrue(BankAccount.isEmailValid("xxx@yyyy.com"));
        //not a border class
        //equivalent class with the tests for one instance of a character
        assertTrue(BankAccount.isEmailValid("xxx_xxx@yyyy.com"));

        // Suffix Tests

        //border class, there is only one character after the "." in suffix
        //no equivalent class
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy.c"));

        //not a border test because no instances of "#" are allowed
        //equivalent class with the test that checked for the same thing in prefix
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy#yyyyyyy.com"));

        //border test because 0 is not an allowed number for instances of "." in suffix
        //no equivalence test
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy"));

        //border test because only one "." is allowed in suffix, here there are two
        //equivalence test with the same thing checked in prefix
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy..com"));

        //this is a border class, as it allows for at least 2 characters after "." in suffix
        //characters after the "."
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.cc"));

        //not a border test
        //not a equivalent test
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy-yyyyyyy.com"));

        //border test of occurence of "."
        //equivalent class with test below
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.org"));

        //border test with the occurence of "."
        //equivalence class with test above
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