package edu.ithaca.dragon.bank;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static edu.ithaca.dragon.bank.Utilities.*;

public class CDAccount extends BankAccount{
    double interest;
    int days;

    /**
     * Creates a CD account with daily interest, and a time limit before anything can be withdrawn
     * @throws IllegalArgumentException if accountID, interest,time limit or balance is invalid
     */
    CDAccount(String accountid, double startingBalance, String password, double interest, int days){
        innerConstruction(accountid, startingBalance, password, interest, days);
    }

    /** Makes a CD account based off named arguments in the map
     * Needs keys 'accountID', 'startingBalance', 'password', 'interest'
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    CDAccount(Map<String, String> arguments){
        try {
            String accountID = arguments.get("accountID");
            double startingBalance = Double.parseDouble(arguments.get("startingBalance"));
            String password = arguments.get("password");
            double interest = Double.parseDouble(arguments.get("interest"));
            int days= Integer.parseInt(arguments.get("days"));
            innerConstruction(accountID, startingBalance, password, interest, days);
        }catch (NullPointerException e){
            throw new IllegalArgumentException("Did not pass all needed arguments");
        }
    }

    private void innerConstruction(String accountid, double startingBalance, String password, double interest, int days){
        if(!isAccountIDValid(accountid)){
            throw new IllegalArgumentException("Account ID invalid");
        }else if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance invalid");
        }else if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest invalid");
        } else if(!isPasswordValid(password)){
            throw new IllegalArgumentException("Password invalid");
        }
        else if (!isDaysValid(days)){
            throw new IllegalArgumentException("Day invalid");
        }

        this.accountID = accountid;
        this.balance = startingBalance;
        this.history = new LinkedList<>();
        this.interest = interest;
        this.password = password;
        this.days = days;
        this.loggedIn = false;
    }

    public void update(){
        // Does CD Account Interest
        balance += balance * interest;
        balance = Math.round(balance *100.0)/100.0;

    }

    public double getInterest(){
        return interest;
    }

    /**
     * @throws IllegalArgumentException if interest invalid
     */
    public void setInterest(double interest){
        if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest Invalid: "+interest);
        }
        this.interest = interest;
    }

    /**
     * checks if interest is valid
     * @param interest possible valid interest
     * @return false if has more than 4 decimal points or <0. Otherwise true
     */
    public static boolean isInterestValid(double interest){
        if(interest<0){
            return false;
        }else if(interest != Math.round(interest*10000)/10000.0){
            return false;
        }
        return true;
    }

    /**
     * Creates a map to use with the map constructor
     * @returns a map
     */
    public static Map<String, String> makeCDMap(String accountID, double startingBalance, String password, double interest, int days){
        Map<String, String> args = new TreeMap<String, String>();
        args.put("accountType", "savings");
        args.put("accountID", accountID);
        args.put("startingBalance", ""+startingBalance);
        args.put("password", password);
        args.put("interest", ""+interest);
        args.put("days", ""+days);
        return args;
    }
}
