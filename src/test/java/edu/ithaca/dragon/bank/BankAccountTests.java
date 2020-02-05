package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTests {

    // Base BankAccount Tests
    // Note: Due to BankAccount being abstract, a CheckingAccount object which implements
    // BankAccount will be created. CheckingAccount does not override any classes defined
    // in BankAccount.
    @Test
    void getBalanceTest() {
        // Valid Tests
        BankAccount bankAccount = new CheckingAccount("0123456789", .01);
        assertEquals(.01, bankAccount.getBalance()); // Boundary

        bankAccount = new CheckingAccount("0123456789", 200);
        assertEquals(200, bankAccount.getBalance()); // Equivalence
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        // Basic Testing
        BankAccount tester = new CheckingAccount("a@b.cc", 100);
        tester.withdraw(.01);
        assertEquals(99.99, tester.getBalance());

        tester = new CheckingAccount("a@b.cc", 100);
        tester.withdraw(50);
        assertEquals(50, tester.getBalance());

        tester = new CheckingAccount("a@b.cc", 100);
        tester.withdraw(100);
        assertEquals(0, tester.getBalance());

        // Decimal Tests
        BankAccount tester_ef = new CheckingAccount("a@b.cc", 100);
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
        BankAccount tester_0 = new CheckingAccount("a@b.cc", 1);
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

        BankAccount ba = new CheckingAccount("a@b.com", 100);

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
        BankAccount fromAccount = new CheckingAccount("a@b.com", 1000);
        BankAccount toAccount = new CheckingAccount("a@b.com", 1000);

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
        BankAccount ef = new CheckingAccount("a@b.com", 1000);
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
        BankAccount ba = new CheckingAccount("0000000000", 100); // Boundary
        assertEquals("0000000000", ba.getAccountID());
        ba = new CheckingAccount("0123456789", 100); // Equivalence
        assertEquals("0123456789", ba.getAccountID());
    }

    @Test
    void historyTest(){
        BankAccount ba = new CheckingAccount("0000000000", 1000);

        // No History
        assertEquals(0, ba.getHistory().size());

        // History
        ba.deposit(10);
        assertEquals(1, ba.getHistory().size());
        assertEquals(10, ba.getHistory().get(0).doubleValue());

        ba.deposit(20);
        assertEquals(1, ba.getHistory().size());
        assertEquals(10, ba.getHistory().get(0).doubleValue());
        assertEquals(20, ba.getHistory().get(1).doubleValue());

        // Cannot Modify History
        assertThrows(UnsupportedOperationException.class, () -> ba.getHistory().add(1d));
    }

    // Checking Account Tests
    // Only testing constructor & overridden methods
    @Test
    void constructorCheckingAccountTest() {
        // PLEASE NOTE:
        // These tests assume that functions
        // isAccountID & isAmountValid
        // are used in implementation.
        // This means we are only testing for
        // number of errors as specific errors
        // are handled by those functions.
        // (otherwise each test in those needs to be here)

        // Correct
        CheckingAccount numberAccount = new CheckingAccount("0123456789", .01);
        assertEquals(.01, numberAccount.getBalance()); // Boundary
        numberAccount = new CheckingAccount("0123456789", 1);
        assertEquals(1, numberAccount.getBalance()); // Equivalence


        // AccountID Wrong
        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount("012345678", -.01)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount("01234567a", -.001)); // Equivalence


        // Balance Wrong
        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount("0123456789", -.01)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount("0123456789", -.001)); // Equivalence
    }

    @Test
    void updateCheckingAccountTest(){
        // Nothing should happen.
        CheckingAccount ca = new CheckingAccount("000000000", .01); // Boundary
        ca.update();
        assertEquals("000000000", ca.getAccountID());
        assertEquals(.01, ca.getBalance());

        ca = new CheckingAccount("012345679", .01); // Boundary
        ca.update();
        assertEquals("012345679", ca.getAccountID());
        assertEquals(.01, ca.getBalance());

        ca = new CheckingAccount("0001112223", 50); // Equivalence
        ca.update();
        assertEquals("0001112223", ca.getAccountID());
        assertEquals(50, ca.getBalance());
    }

    // Savings Account Tests
    // Only testing constructor & overridden methods
    @Test
    void constructorSavingsAccountTest(){

    }

    @Test
    void updateSavingsAccountTest(){

    }


}