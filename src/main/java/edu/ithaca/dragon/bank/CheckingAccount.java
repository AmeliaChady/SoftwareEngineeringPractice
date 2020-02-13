package edu.ithaca.dragon.bank;


import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static edu.ithaca.dragon.bank.Utilities.*;

public class CheckingAccount extends BankAccount{

    /**
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    public CheckingAccount(String accountID, double startingBalance, String password){
        innerConstruction(accountID, startingBalance, password);
    }

    /** Makes a checking account based off named arguments in the map
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    public CheckingAccount(Map<String, String> arguments){
        try {
            String accountID = arguments.get("accountID");
            double startingBalance = Double.parseDouble(arguments.get("startingBalance"));
            String password = arguments.get("password");
            innerConstruction(accountID, startingBalance, password);
        }catch (NullPointerException e){
            throw new IllegalArgumentException("Did not pass all needed arguments");
        }
    }

    private void innerConstruction(String accountID, double startingBalance, String password){
        if(!isAccountIDValid(accountID)){
            throw new IllegalArgumentException("Account ID "+accountID+" is Invalid!");
        }
        if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance: " + startingBalance + " is invalid, cannot create account");
        }
        if(!isPasswordValid(password)){
            throw new IllegalArgumentException("Password invalid");
        }
        this.accountID = accountID;
        this.balance = startingBalance;
        this.history = new LinkedList<>();
        this.password = password;
        this.loggedIn = false;
    }

    @Override
    public void update() {
        // Is nothing, as it is not needed for specific class usage.
        // Needed to allow for usability
    }

    /**
     * Creates a map to use with the map constructor
     * @returns a map
     */
    public static Map<String, String> makeCheckingMap(String accountID, double startingBalance, String password){
        Map<String, String> args = new TreeMap<String, String>();
        args.put("accountType", "checking");
        args.put("accountID", accountID);
        args.put("startingBalance", ""+startingBalance);
        args.put("password", password);
        return args;
    }
}


