package edu.ithaca.dragon.bank;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static edu.ithaca.dragon.bank.Utilities.*;

public class SavingsAccount extends BankAccount{
    double interest;

    /**
     * Creates a savings account with daily interest
     * @throws IllegalArgumentException if accountID, interest or balance is invalid
     */
    SavingsAccount(String accountid, double startingBalance, String password, double interest){
        innerConstruction(accountid, startingBalance, password, interest);
    }

    /** Makes a savings account based off named arguments in the map
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    SavingsAccount(Map<String, String> arguments){
        try {
            String accountID = arguments.get("accountID");
            double startingBalance = Double.parseDouble(arguments.get("startingBalance"));
            String password = arguments.get("password");
            double interest = Double.parseDouble(arguments.get("interest"));
            innerConstruction(accountID, startingBalance, password, interest);
        }catch (NullPointerException e){
            throw new IllegalArgumentException("Did not pass all needed arguments");
        }
    }

    private void innerConstruction(String accountid, double startingBalance, String password, double interest){
        if(!isAccountIDValid(accountid)){
            throw new IllegalArgumentException("Account ID invalid");
        }else if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance invalid");
        }else if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest invalid");
        } else if(!isPasswordValid(password)){
            throw new IllegalArgumentException("Password invalid");
        }

        this.accountID = accountid;
        this.balance = startingBalance;
        this.history = new LinkedList<>();
        this.interest = interest;
        this.password = password;
        this.loggedIn = false;
    }

    public void update(){
        // Does Savings Account Interest
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
    public static Map<String, String> makeSavingsMap(String accountID, double startingBalance, String password, double interest){
        Map<String, String> args = new TreeMap<String, String>();
        args.put("accountType", "savings");
        args.put("accountID", accountID);
        args.put("startingBalance", ""+startingBalance);
        args.put("password", password);
        args.put("interest", ""+interest);
        return args;
    }
}
