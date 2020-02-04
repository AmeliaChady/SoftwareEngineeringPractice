package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTests {

    // Base BankAccount Tests
    // Note: Due to BankAccount being abstract, a CheckingAccount object which implements
    // BankAccount will be created. CheckingAccount does not override any classes defined
    // in BankAccount.
    @Test
    void withdrawTest() throws InsufficientFundsException{
        // Basic Testing
        CheckingAccount tester = new CheckingAccount("a@b.cc", 100);
        tester.withdraw(.01);
        assertEquals(99.99, tester.getBalance());

        tester = new CheckingAccount("a@b.cc", 100);
        tester.withdraw(50);
        assertEquals(50, tester.getBalance());

        tester = new CheckingAccount("a@b.cc", 100);
        tester.withdraw(100);
        assertEquals(0, tester.getBalance());

        // Decimal Tests
        CheckingAccount tester_ef = new CheckingAccount("a@b.cc", 100);
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(.001));
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(.0001));

        // Negative Tests
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(-.01));
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(-1));

        // Overdraw Tests
        assertThrows(InsufficientFundsException.class, () -> tester_ef.withdraw(100.01));
        assertThrows(InsufficientFundsException.class, () -> tester_ef.withdraw(500));

        // Dual Decimal/Negative Tests
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(-.001));
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(-1.001));
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(-.0001));

        // Dual Decimal/Overdraw Tests
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(100.001));
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(500.001));
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(100.0001));

        // Zero as Input Test
        assertThrows(IllegalArgumentException.class, () -> tester_ef.withdraw(0));

        // Balance 0 Testing
        CheckingAccount tester_0 = new CheckingAccount("a@b.cc", 1);
        tester_0.withdraw(1);

        assertThrows(IllegalArgumentException.class, () -> tester_0.withdraw(0));
        assertThrows(InsufficientFundsException.class, () -> tester_0.withdraw(.01));
        assertThrows(InsufficientFundsException.class, () -> tester_0.withdraw(10));
    }

    @Test
    void depositTest(){
        // NOTE:
        // Tests assuming that
        // a. isAmountValid is in use
        // b. isAmountValid does all errors
        // All tests that would go with isAmountValid are assumed to be correct as we are showing we are using

        CheckingAccount ba = new CheckingAccount("a@b.com", 100);

        ba.deposit(1);
        assertEquals(101, ba.getBalance()); // Equivalence
        ba.deposit(.01);
        assertEquals(101.01, ba.getBalance()); // Boundary

        // Errors
        assertThrows(IllegalArgumentException.class, () -> ba.deposit(-1));
        assertThrows(IllegalArgumentException.class, () -> ba.deposit(-.001));


    }

    @Test
    void transferTest() throws InsufficientFundsException{
        // NOTE:
        // Tests assuming that
        // a. isAmountValid is in use
        // b. isAmountValid does all errors
        // All tests that would go with isAmountValid are assumed to be correct as we are showing we are using

        // NORMAL TESTS
        CheckingAccount fromAccount = new CheckingAccount("a@b.com", 1000);
        CheckingAccount toAccount = new CheckingAccount("a@b.com", 1000);

        fromAccount.transfer(toAccount, .01); // Boundary
        assertEquals(999.99, fromAccount.getBalance());
        assertEquals(1000.01, toAccount.getBalance());

        fromAccount.transfer(toAccount, 499.99); // Equivalence
        assertEquals(500, fromAccount.getBalance());
        assertEquals(1500, toAccount.getBalance());

        fromAccount.transfer(toAccount, 500); // Boundary
        assertEquals(0, fromAccount.getBalance());
        assertEquals(2000, toAccount.getBalance());

        // BANK ACCOUNT INVALID
        CheckingAccount ef = new CheckingAccount("a@b.com", 1000);
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(null, 100));
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(ef, 100));

        // AMOUNT INVALID
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(toAccount, -1)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(toAccount, -.001)); // Equivalence
        assertThrows(InsufficientFundsException.class, () -> ef.transfer(toAccount, 1000.1)); // Boundary
        assertThrows(InsufficientFundsException.class, () -> ef.transfer(toAccount, 10000)); // Equivalence

    }

    @Test
    void getAccountIDTest(){

    }

    @Test
    void getBalanceTest() {
        // Valid Tests
        CheckingAccount bankAccount = new CheckingAccount("a@b.com", .01);
        assertEquals(.01, bankAccount.getBalance()); // Boundary

        bankAccount = new CheckingAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance()); // Equivalence

    }

    @Test
    void getHistoryTest(){

    }

    // Checking Account Tests
    // Only testing constructor & overridden methods
    @Test
    void constructorCheckingAccountTest() {
        // PLEASE NOTE:
        // These tests assume that functions
        // isEmailValid & isAmountValid
        // are used in implementation.
        // This means we are only testing for
        // number of errors as specific errors
        // are handled by those functions.
        // (otherwise each test in those needs to be here)

        // Account
        CheckingAccount numberAccount = new CheckingAccount("a@b.com", .01);
        assertEquals(.01, numberAccount.getBalance()); // Boundary
        numberAccount = new CheckingAccount("a@b.com", 1);
        assertEquals(1, numberAccount.getBalance()); // Equivalence

        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount("a@b.com", -.01)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount("a@b.com", -.001)); // Equivalence
    }

    @Test
    void updateCheckingAccountTest(){

    }


}