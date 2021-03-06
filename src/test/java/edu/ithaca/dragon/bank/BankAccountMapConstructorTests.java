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
        assertEquals("0000000000", c.getAccountID());
        assertEquals(1012, c.getBalance());
        assertEquals("password!2", c.password);

        // Doesn't work
        // AccountID invalid
        Map<String, String> map1a = CheckingAccount.makeCheckingMap("0",1, "password!1");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map1a));
        Map<String, String> map1b = CheckingAccount.makeCheckingMap("0",1, "password!1");
        map1b.remove("accountID");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map1b));

        // startingBalance invalid
        Map<String, String> map2a = CheckingAccount.makeCheckingMap("0123456789", -1, "password!1");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map2a));
        Map<String, String> map2b = CheckingAccount.makeCheckingMap("0123456789", -1, "password!1");
        map2b.remove("startingBalance");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map2b));

        // password invalid
        Map<String, String> map3a = CheckingAccount.makeCheckingMap("0123456789",1, "p");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map3a));
        Map<String, String> map3b = CheckingAccount.makeCheckingMap("0123456789",1, "p");
        map3b.remove("password");
        assertThrows(IllegalArgumentException.class,
                () -> new CheckingAccount(map3b));
    }

    @Test
    void makeMapSavingsAccountTest(){
        Map<String, String> args = SavingsAccount.makeSavingsMap("0123456789", .01, "password!1", .05);
        assertEquals("savings", args.get("accountType"));
        assertEquals("0123456789", args.get("accountID"));
        assertEquals("0.01", args.get("startingBalance"));
        assertEquals("password!1", args.get("password"));
        assertEquals("0.05", args.get("interest"));

        args = SavingsAccount.makeSavingsMap("0000000000", 1012, "password!2", 1);
        assertEquals("savings", args.get("accountType"));
        assertEquals("0000000000", args.get("accountID"));
        assertEquals("1012.0", args.get("startingBalance"));
        assertEquals("password!2", args.get("password"));
        assertEquals("1.0", args.get("interest"));
    }

    @Test
    void mapConstructorSavingsAccountTest() {
        // assumes makeSavingsMap works

        // Works
        Map<String, String> map = SavingsAccount.makeSavingsMap("0000000000", 1012, "password!2", .01);
        SavingsAccount s = new SavingsAccount(map);
        assertEquals("0000000000", s.getAccountID());
        assertEquals(1012, s.getBalance());
        assertEquals("password!2", s.password);
        assertEquals(.01, s.getInterest());

        // Doesn't work
        // AccountID invalid
        Map<String, String> map1a = SavingsAccount.makeSavingsMap("0",1, "password!1", .01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map1a));
        Map<String, String> map1b = SavingsAccount.makeSavingsMap("0",1, "password!1", .01);
        map1b.remove("accountID");
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map1b));

        // startingBalance invalid
        Map<String, String> map2a = SavingsAccount.makeSavingsMap("0123456789", -1, "password!1", .01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map2a));
        Map<String, String> map2b = SavingsAccount.makeSavingsMap("0123456789", -1, "password!1", .01);
        map2b.remove("startingBalance");
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map2b));

        // password invalid
        Map<String, String> map3a = SavingsAccount.makeSavingsMap("0123456789",1, "p", .01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map3a));
        Map<String, String> map3b = SavingsAccount.makeSavingsMap("0123456789",1, "p", .01);
        map3b.remove("password");
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map3b));

        // interest invalid
        Map<String, String> map4a = SavingsAccount.makeSavingsMap("0123456789",1, "password!1", -.01);
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map4a));
        Map<String, String> map4b = SavingsAccount.makeSavingsMap("0123456789",1, "password!1", -.01);
        map4b.remove("interest");
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(map4b));
    }
}
