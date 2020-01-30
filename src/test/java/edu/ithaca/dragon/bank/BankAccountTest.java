package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicLookAndFeel;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        // Valid Tests
        BankAccount bankAccount = new BankAccount("a@b.com", .01);
        assertEquals(.01, bankAccount.getBalance()); // Boundary

        bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance()); // Equivalence

    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        // Basic Testing
        BankAccount tester = new BankAccount("a@b.cc", 100);
        tester.withdraw(.01);
        assertEquals(99.99, tester.getBalance());

        tester = new BankAccount("a@b.cc", 100);
        tester.withdraw(50);
        assertEquals(50, tester.getBalance());

        tester = new BankAccount("a@b.cc", 100);
        tester.withdraw(100);
        assertEquals(0, tester.getBalance());

        // Decimal Tests
        BankAccount tester_ef = new BankAccount("a@b.cc", 100);
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
        BankAccount tester_0 = new BankAccount("a@b.cc", 1);
        tester_0.withdraw(1);

        assertThrows(IllegalArgumentException.class, () -> tester_0.withdraw(0));
        assertThrows(InsufficientFundsException.class, () -> tester_0.withdraw(.01));
        assertThrows(InsufficientFundsException.class, () -> tester_0.withdraw(10));
    }

    @Test
    void isAmountValidTest(){
        // Valid Tests
        assertEquals(true, BankAccount.isAmountValid(.01)); // Boundary
        assertEquals(true, BankAccount.isAmountValid(10)); // Equivalence (Normal Cases)

        // Invalid Tests
        // Zero Case
        assertEquals(false, BankAccount.isAmountValid(0)); // Boundary

        // Decimal
        assertEquals(false, BankAccount.isAmountValid(.001)); // Boundary
        assertEquals(false, BankAccount.isAmountValid(.0001)); // Equivalence (Decimals)

        // Negative
        assertEquals(false, BankAccount.isAmountValid(-.01)); // Boundary
        assertEquals(false, BankAccount.isAmountValid(-1)); // Equivalence (Negatives)

        // Decimal & Negative
        assertEquals(false, BankAccount.isAmountValid(-.001)); // Boundary
        assertEquals(false, BankAccount.isAmountValid(-.0001)); // Equivalence (Moving Decimal)
        assertEquals(false, BankAccount.isAmountValid(-1.001)); // Equivalence (Moving Negative)
    }

    public static final String allowedNormalCharacters = "abcdefghijklmnopqrstuvwxyz" +
                                                         "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                                                         "0123456789";
    public static final String prefixAllowedSpecialCharacters = "-_.";
    // Assumption: Only Caring About Keys On QUERTY Layout Keyboard
    public static final String prefixNotAllowedSpecialCharacters = " ,<>/?;:[{]}|!#$%^&*=+";

    public static final String suffixAllowedSpecialCharacters = "-"; // Not countind single . for address ending
    // Assumption: Only Caring About Keys On QUERTY Layout Keyboard
    public static final String suffixNotAllowedSpecialCharacters = " ,<>/?;:[{]}|!#$%^&*_=+";
    @Test
    void isEmailValidTest(){
    // PREFIX - Basic Characters
        for (char c : allowedNormalCharacters.toCharArray()){
            assertTrue(BankAccount.isEmailValid(c+"@yyyy.com"), "Failed On: "+c+"@yyyy.com"); // Boundary
            assertTrue(BankAccount.isEmailValid(c+c+c+"@yyyy.com"), "Failed On: "+c+c+c+"@yyyy.com" ); // Equivalence
        }

    // PREFIX - Allowed Special Cases Tests
        for (char c : prefixAllowedSpecialCharacters.toCharArray()) {
            prefixAllowedSpecialCharacterTesting(c+"");
        }

    // PREFIX - Not Allowed Special Character Tests
        for (char c : prefixNotAllowedSpecialCharacters.toCharArray()){
            prefixNotAllowedSpecialCharacterTesting(c+"");
        }

    // PREFIX - Basic Characters
        for (char c : allowedNormalCharacters.toCharArray()){
            assertTrue(BankAccount.isEmailValid("xxx@"+c+".com"), "Failed On: "+"xxx@"+c+".com"); // Boundary
            assertTrue(BankAccount.isEmailValid("xxx@"+c+c+c+".com"), "Failed On: "+"xxx@"+c+c+c+".com"); // Equivalence
            assertTrue(BankAccount.isEmailValid("xxx@yyy.c"+c), "Failed On: "+"xxx@yyy.c"+c); // Boundary
            assertTrue(BankAccount.isEmailValid("xxx@yyy.c"+c+c), "Failed On: "+"xxx@yyy.c"+c+c); // Equivalence
        }

    // SUFFIX - Ending Testing
        assertFalse(BankAccount.isEmailValid("xxx@yyyy.")); // Border - Nothing after period
        assertFalse(BankAccount.isEmailValid("xxx@yyyy.c")); // Border - 1 Character after period
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyycom")); // Border (Single Case)
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy..com")); // Border
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyyy...com")); // Equivalence
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyy.y.com")); // Border
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyy.y...com")); // Equivalence

        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.cc")); // Border
        assertTrue(BankAccount.isEmailValid("xxx.xxx@yyyy.org")); // Equivalence

    // SUFFIX - Allowed Special Character Testing
        for(char c : suffixAllowedSpecialCharacters.toCharArray()){
            suffixAllowedSpecialCharacterTesting(c+"");
        }

    // SUFFIX - Not Allowed Special Character Testing
        for(char c : suffixNotAllowedSpecialCharacters.toCharArray()){
            suffixNotAllowedSpecialCharacterTesting(c+"");
        }
    }

    void prefixAllowedSpecialCharacterTesting(String asc){
        // Left Edge
        assertFalse(BankAccount.isEmailValid(asc+"xxx@yyyy.com"), "Failed On: "+asc+"xxx@yyyy.com"); // Boundary (Single Case)

        // Right Edge
        assertFalse(BankAccount.isEmailValid("xxx"+asc+"@yyyy.com"), "Failed On: "+"xxx"+asc+"@yyyy.com"); // Boundary (Single Case)

        // Inside
        assertTrue(BankAccount.isEmailValid("x"+asc+"xxx@yyyy.com"), "Failed On: "+"x"+asc+"xxx@yyyy.com"); // Boundary (Left Edge)
        assertTrue(BankAccount.isEmailValid("xx"+asc+"xx@yyyy.com"), "Failed On: "+"xx"+asc+"xx@yyyy.com"); // Equivalence
        assertTrue(BankAccount.isEmailValid("xxx"+asc+"x@yyyy.com"), "Failed On: "+"xxx"+asc+"x@yyyy.com"); // Boundary (Right Edge)

        // Next To Each Other
        assertFalse(BankAccount.isEmailValid("xx"+asc+asc+"xx@yyyy.com"), "Failed On: "+"xx"+asc+asc+"xx@yyyy.com"); // Boundary (2 special characters)
        assertFalse(BankAccount.isEmailValid("xx"+asc+asc+asc+"xx@yyyy.com"), "Failed On: "+"xx"+asc+asc+asc+"xx@yyyy.com"); // Equivalence

        // Multiple, but Spaced Apart
        assertTrue(BankAccount.isEmailValid("xx"+asc+"x"+asc+"x@yyyy.com"), "Failed On: "+"xx"+asc+"x"+asc+"x@yyyy.com"); // Boundary (2 Characters, 1 Space)
        assertTrue(BankAccount.isEmailValid("x"+asc+"xx"+asc+"x@yyyy.com"), "Failed On: "+"x"+asc+"xx"+asc+"x@yyyy.com"); // Equivalence for Space
        assertTrue(BankAccount.isEmailValid("x"+asc+"x"+asc+"x"+asc+"x@yyyy.com"), "Failed On: "+"x"+asc+"x"+asc+"x"+asc+"x@yyyy.com"); // Equivalence for Numbers
        assertTrue(BankAccount.isEmailValid("x"+asc+"xx"+asc+"xx"+asc+"x@yyyy.com"), "Failed On: "+"x"+asc+"xx"+asc+"xx"+asc+"x@yyyy.com"); // Equivalence for Space & Numbers (Needed?)
    }

    void prefixNotAllowedSpecialCharacterTesting(String asc) {
        assertFalse(BankAccount.isEmailValid("x" + asc + "x@yyyy.com"), "Failed On: " + "x" + asc + "x@yyyy.com"); // Boundary
        assertFalse(BankAccount.isEmailValid("x" + asc + asc + "x@yyyy.com"), "Failed On: " + "x" + asc + asc + "x@yyyy.com"); // Equivalence
    }

    void suffixAllowedSpecialCharacterTesting(String asc){
        assertFalse(BankAccount.isEmailValid("xxx@"+asc+"yy.com"),"Failed On: "+"xxx@"+asc+"yy.com"); // Boundary
        assertTrue(BankAccount.isEmailValid("xxx@y"+asc+"y.com"),"Failed On: "+"xxx@y"+asc+"y.com"); // Equivalence
        assertTrue(BankAccount.isEmailValid("xxx@yy"+asc+".com"),"Failed On: "+"xxx@yy"+asc+".com"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@"+asc+"y"+asc+".com"),"Failed On: "+"xxx@"+asc+"y"+asc+".com"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@"+asc+"yy"+asc+".com"),"Failed On: "+"xxx@"+asc+"yy"+asc+".com"); // Equivalence

        assertFalse(BankAccount.isEmailValid("xxx@yy"+asc+asc+".com"),"Failed On: "+"xxx@yy"+asc+asc+".com"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@yy"+asc+asc+asc+".com"),"Failed On: "+"xxx@yy"+asc+asc+asc+".com"); // Equivalence

    }

    void suffixNotAllowedSpecialCharacterTesting(String asc){
        assertFalse(BankAccount.isEmailValid("xxx@yy"+asc+".com"), "Failed On: "+ "xxx@yy"+asc+".com"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@y"+asc+"y"+asc+".com"), "Failed On: "+"xxx@y"+asc+"y"+asc+".com"); // Equivalence
    }

    @Test
    void constructorTest() {
        // PLEASE NOTE:
        // These tests assume that functions
        // isEmailValid & isAmountValid
        // are used in implementation.
        // This means we are only testing for
        // number of errors as specific errors
        // are handled by those functions.
        // (otherwise each test in those needs to be here)

        // EMAIL
        BankAccount emailAcct = new BankAccount("ab@cd.com", 1);
        assertEquals("ab@cd.com", emailAcct.getEmail()); // Boundary
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(".@a.com", 1)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(".@.com", 1)); // Equivalence

        // Account
        BankAccount numberAccount = new BankAccount("a@b.com", .01);
        assertEquals(.01, numberAccount.getBalance()); // Boundary
        numberAccount = new BankAccount("a@b.com", 1);
        assertEquals(1, numberAccount.getBalance()); // Equivalence

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -.01)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -.001)); // Equivalence
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        // NOTE:
        // Tests assuming that
        // a. isAmountValid is in use
        // b. isAmountValid does all errors
        // All tests that would go with isAmountValid are assumed to be correct as we are showing we are using

        // NORMAL TESTS
        BankAccount fromAccount = new BankAccount("a@b.com", 1000);
        BankAccount toAccount = new BankAccount("a@b.com", 1000);

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
        BankAccount ef = new BankAccount("a@b.com", 1000);
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(null, 100));
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(ef, 100));

        // AMOUNT INVALID
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(toAccount, -1)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(toAccount, -.001)); // Equivalence
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(toAccount, 1000.1)); // Boundary
        assertThrows(IllegalArgumentException.class, () -> ef.transfer(toAccount, 10000)); // Equivalence

    }

    @Test
    void depositTest(){
        // NOTE:
        // Tests assuming that
        // a. isAmountValid is in use
        // b. isAmountValid does all errors
        // All tests that would go with isAmountValid are assumed to be correct as we are showing we are using

        BankAccount ba = new BankAccount("a@b.com", 100);

        ba.deposit(1);
        assertEquals(101, ba.getBalance()); // Equivalence
        ba.deposit(.01);
        assertEquals(101.01, ba.getBalance()); // Boundary

        // Errors
        assertThrows(IllegalArgumentException.class, () -> ba.deposit(-1));
        assertThrows(IllegalArgumentException.class, () -> ba.deposit(-.001));


    }

}