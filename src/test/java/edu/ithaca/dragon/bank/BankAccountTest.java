package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicLookAndFeel;

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


    public static final String allowedNormalCharacters = "abcdefghijklmnopqrstuvwxyz" +
                                                         "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                                                         "0123456789";
    public static final String prefixAllowedSpecialCharacters = "-_.";
    // Assumption: Only Caring About Keys On QUERTY Layout Keyboard
    public static final String prefixNotAllowedSpecialCharacters = " ,<>/?;:'\"[{]}\\|!@#$%^&*()=+";

    public static final String suffixAllowedSpecialCharacters = "-"; // Not countind single . for address ending
    // Assumption: Only Caring About Keys On QUERTY Layout Keyboard
    public static final String suffixNotAllowedSpecialCharacters = " ,<>/?;:'\"[{]}\\|!@#$%^&*()_=+";

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
        assertFalse(BankAccount.isEmailValid("xxx.xxx@yyy.y.com")); // Border
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
        assertTrue(BankAccount.isEmailValid("xxx@"+asc+"yy.com"),"Failed On: "+"xxx@"+asc+"yy.com"); // Boundary
        assertTrue(BankAccount.isEmailValid("xxx@y"+asc+"y.com"),"Failed On: "+"xxx@y"+asc+"y.com"); // Equivalence
        assertTrue(BankAccount.isEmailValid("xxx@yy"+asc+".com"),"Failed On: "+"xxx@yy"+asc+".com"); // Boundary
        assertTrue(BankAccount.isEmailValid("xxx@"+asc+"y"+asc+".com"),"Failed On: "+"xxx@"+asc+"y"+asc+".com"); // Boundary
        assertTrue(BankAccount.isEmailValid("xxx@"+asc+"yy"+asc+".com"),"Failed On: "+"xxx@"+asc+"yy"+asc+".com"); // Equivalence

        assertFalse(BankAccount.isEmailValid("xxx@yy"+asc+asc+".com"),"Failed On: "+"xxx@yy"+asc+asc+".com"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@yy"+asc+asc+asc+".com"),"Failed On: "+"xxx@yy"+asc+asc+asc+".com"); // Equivalence
        assertFalse(BankAccount.isEmailValid("xxx@yyy."+asc+"om"),"Failed On: "+"xxx@yyy."+asc+"om"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@yyy.c"+asc+"m"),"Failed On: "+"xxx@yyy.c"+asc+"m"); // Equivalence
        assertFalse(BankAccount.isEmailValid("xxx@yyy.co"+asc),"Failed On: "+"xxx@yyy.co"+asc); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@yyy.c"+asc+asc),"Failed On: "+"xxx@yyy.c"+asc+asc); // Equivalence
        assertFalse(BankAccount.isEmailValid("xxx@yyy."+asc+"o"+asc),"Failed On: "+"xxx@yyy."+asc+"o"+asc); // Equivalence
    }

    void suffixNotAllowedSpecialCharacterTesting(String asc){
        assertFalse(BankAccount.isEmailValid("xxx@yy"+asc+".com"), "Failed On: "+ "xxx@yy"+asc+".com"); // Boundary
        assertFalse(BankAccount.isEmailValid("xxx@y"+asc+"y"+asc+".com"), "Failed On: "+"xxx@y"+asc+"y"+asc+".com"); // Equivalence
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