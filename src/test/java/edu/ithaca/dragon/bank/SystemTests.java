package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {



    //---------------Kerry---------------//

    @Test
    public void fullDayTest(){
        AccountLibrary al = new AccountLibrary();
        BankAccount checkingAccount = new CheckingAccount("0000000001", 200.54);
        BankAccount savingsAccount = new SavingsAccount("0000000002", 1.00, 0.25);
        al.accounts.put(checkingAccount.getAccountID(), checkingAccount);
        al.accounts.put(savingsAccount.getAccountID(), savingsAccount);

        assertEquals(201.54, al.calcTotalAssets());


    }

    //---------------Amelia---------------//
    @Test
    public void suspiciousDayTest(){

    }

    //---------------Jolie---------------//
}
