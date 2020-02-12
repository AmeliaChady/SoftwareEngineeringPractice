package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static edu.ithaca.dragon.bank.Utilities.*;

public class UtilitiesTest {

    @Test
    void isAmountValidTest(){
        // Valid Tests
        assertEquals(true, isAmountValid(.01)); // Boundary
        assertEquals(true, isAmountValid(10)); // Equivalence (Normal Cases)

        // Invalid Tests
        // Zero Case
        assertEquals(false, isAmountValid(0)); // Boundary

        // Decimal
        assertEquals(false, isAmountValid(.001)); // Boundary
        assertEquals(false, isAmountValid(.0001)); // Equivalence (Decimals)

        // Negative
        assertEquals(false, isAmountValid(-.01)); // Boundary
        assertEquals(false, isAmountValid(-1)); // Equivalence (Negatives)

        // Decimal & Negative
        assertEquals(false, isAmountValid(-.001)); // Boundary
        assertEquals(false, isAmountValid(-.0001)); // Equivalence (Moving Decimal)
        assertEquals(false, isAmountValid(-1.001)); // Equivalence (Moving Negative)
    }

    @Test
    void isAccountIDValidTest(){

        // Length Tests
        assertEquals(true, isAccountIDValid("0000000000")); // Boundary (Single Case)

        assertEquals(false, isAccountIDValid("000000000")); // Boundary (Smaller)
        assertEquals(false, isAccountIDValid("00000000")); // Equivalence (Smaller)

        assertEquals(false, isAccountIDValid("00000000000")); // Boundary (Larger)
        assertEquals(false, isAccountIDValid("000000000000")); // Equivalence (Larger)

        // Character Tests
        // Note: Assuming that it will work against all not allowed characters, only testing against two
        assertEquals(true, isAccountIDValid("0123456789")); // Boundary

        assertEquals(false, isAccountIDValid("01234.6789")); // Boundary
        assertEquals(false, isAccountIDValid("0.234.6789")); // Equivalence
        assertEquals(false, isAccountIDValid("01f3456789")); // Equivalence
        assertEquals(false, isAccountIDValid("01f34.6789")); // Equivalence

    }

    @Test
    void isPasswordValidTest(){
        //Length Tests
        assertFalse(isPasswordValid("aaww")); //Boundary (Smaller)
        assertFalse(isPasswordValid("asdfghjklasdfgh")); //Boundary (Larger)
        assertFalse(isPasswordValid("a")); //Equivalence (Smaller)
        assertFalse(isPasswordValid("asdfghjklkjhgfdsasdfghjkl")); //Equivalence (Larger)

        //Character Tests
        assertFalse(isPasswordValid("asdfghjkl")); //No number or special character
        assertFalse(isPasswordValid("asdfghjkl2")); //Number but no special character
        assertFalse(isPasswordValid("asdfghjkl@"));//Special character but no number

        //Valid
        assertTrue(isPasswordValid("asdfghjkl2!"));
        assertTrue(isPasswordValid("edrftygbj3j3&@"));
    }


}

