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


}

