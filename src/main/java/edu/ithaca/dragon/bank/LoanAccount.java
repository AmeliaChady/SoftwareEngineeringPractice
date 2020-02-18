package edu.ithaca.dragon.bank;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import static edu.ithaca.dragon.bank.Utilities.*;

public class LoanAccount extends BankAccount{

    double interest;
    /**
     * Creates a loan account with daily interest
     * @throws IllegalArgumentException if accountID, interest or balance is invalid
     */
    LoanAccount(String accountid, double startingBalance, String password, double interest){
        innerConstruction(accountid, startingBalance, password, interest);
    }

    /** Makes a loan account based off named arguments in the map
     * Needs keys 'accountID', 'startingBalance', 'password', 'interest'
     * @throws IllegalArgumentException if accountID or balance is invalid
     */
    LoanAccount(Map<String, String> arguments){
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
        }else if(!isInterestValid(interest)){
            throw new IllegalArgumentException("Interest invalid");
        } else if(!isPasswordValid(password)){
            throw new IllegalArgumentException("Password invalid");
        }else if(startingBalance>=0){
            throw new IllegalArgumentException("Balance invalid");
        }
        
        this.history = new LinkedList<>();
        this.password = password;
        this.accountID = accountid;
        this.balance = startingBalance;
        this.interest = interest;
        this.loggedIn = false;
    }

    public void update(){
        // Does loan Account Interest
        balance += balance * interest;
        balance = Math.round(balance *100.0)/100.0;

    }

    public void withdraw(double amount) throws AccountFrozenException, InsufficientFundsException{
        // Please Keep First - Account Freeze has priority
        if (isAccountFrozen()){
            throw new AccountFrozenException("Account is frozen");
        }
        if(!Utilities.isAmountValid(amount)){
            throw new IllegalArgumentException("ERROR: invalid amount");
        }
        if(!loggedIn){
            throw new IllegalArgumentException("ERROR: must be logged in");
        }

        balance -= amount;
        balance -= amount*interest;
        balance = Math.round(balance *100.0)/100.0;
        updateHistory(amount, false);
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
    public static Map<String, String> makeLoanMap(String accountID, double startingBalance, String password, double interest){
        Map<String, String> args = new TreeMap<String, String>();
        args.put("accountType", "loan");
        args.put("accountID", accountID);
        args.put("startingBalance", ""+startingBalance);
        args.put("password", password);
        args.put("interest", ""+interest);
        return args;
    }
}
