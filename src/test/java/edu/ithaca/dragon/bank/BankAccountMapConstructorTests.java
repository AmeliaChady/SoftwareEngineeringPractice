package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Map;

public class BankAccountMapConstructorTests {

    @Test
    void makeMapCheckingAccountTest(){
        Map<String, String> args = CheckingAccount.makeCheckingMap("0123456789", .01, "password!1");
        assertEquals("checking", args.get("accountType"));
        assertEquals("0123456789", args.get("accountID"));
        assertEquals("0.01", args.get("startingBalance"));
        assertEquals("password!1", args.get("password"));

        args = CheckingAccount.makeCheckingMap("0000000000", 1012, "password!2");
        assertEquals("checking", args.get("accountType"));
        assertEquals("0000000000", args.get("accountID"));
        assertEquals("1012.0", args.get("startingBalance"));
        assertEquals("password!2", args.get("password"));
    }

    @Test
    void mapConstructorCheckingAccountTest() {
        // assumes makeCheckingMap works

        // Works
        Map<String, String> map = CheckingAccount.makeCheckingMap("0000000000", 1012, "password!2");
        CheckingAccount c = new CheckingAccount(map);

        // Doesn't work
        // AccountID invalid
        Map<String, String> map1 = CheckingAccount.makeCheckingMap("0",1, "password!1");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map1));
        // startingBalance invalid
        Map<String, String> map2 = CheckingAccount.makeCheckingMap("0123456789", -1, "password!1");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map2));
        // password invalid
        Map<String, String> map3 = CheckingAccount.makeCheckingMap("0123456789",1, "p");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map3));
    }

    @Test
    void makeMapSavingsAccountTest(){
        Map<String, String> args = SavingsAccount.makeSavingsMap("0123456789", .01, "password!1", .05);
        assertEquals("savings", args.get("accountType"));
        assertEquals("0123456789", args.get("accountID"));
        assertEquals(".01", args.get("startingBalance"));
        assertEquals("password!1", args.get("password"));
        assertEquals(".05", args.get("interest"));

        args = SavingsAccount.makeSavingsMap("0000000000", 1012, "password!2", 1);
        assertEquals("savings", args.get("accountType"));
        assertEquals("0000000000", args.get("accountID"));
        assertEquals("1012", args.get("startingBalance"));
        assertEquals("password!2", args.get("password"));
        assertEquals("1", args.get("interest"));
    }

    @Test
    void mapConstructorSavingsAccountTest() {
        // assumes makeSavingsMap works

        // Works
        Map<String, String> map = SavingsAccount.makeSavingsMap("0000000000", 1012, "password!2", .01);
        SavingsAccount c = new SavingsAccount(map);

        // Doesn't work
        // AccountID invalid
        Map<String, String> map1 = SavingsAccount.makeSavingsMap("0",1, "password!1", .01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map1));
        // startingBalance invalid
        Map<String, String> map2 = SavingsAccount.makeSavingsMap("0123456789", -1, "password!1", .01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map2));
        // password invalid
        Map<String, String> map3 = SavingsAccount.makeSavingsMap("0123456789",1, "p", .01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map3));
        // interest invalid
        Map<String, String> map4 = SavingsAccount.makeSavingsMap("0123456789",1, "password!1", -.01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map3));
    }
}
