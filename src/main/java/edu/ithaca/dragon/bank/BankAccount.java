package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email or balance is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (!isEmailValid(email)){
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        else if(!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Balance: " + startingBalance + " is invalid, cannot create account");
        }
        this.email = email;
        this.balance = startingBalance;
    }

    /**
     * Transfers some amount of money to another bank account
     * @param transferTo BankAccount to transfer to
     * @param amount amount to transfer
     * @throws InsufficientFundsException if amount is greater than balance
     * @throws IllegalArgumentException if amount is negative or has more than 2 decimals
     */
    public void transfer(BankAccount transferTo, double amount) throws InsufficientFundsException{

    }

    /**
     * Adds some amount of money to the account
     * @param amount amount to deposit
     * @throws IllegalArgumentException if amount has more than 2 decimals or is negative
     */
    public void deposit(double amount){

    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws IllegalArgumentException when the amount is too large or negative
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Argument isn't non-negative with at max 2 decimal points");
        }
        //if amount trying to withdraw is greater than balance
        if (balance < amount){
            throw new InsufficientFundsException("ERROR: Not enough funds to withdraw that amount.");

        }
        balance -= amount;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }


    /**
     * Takes value and decides if it is a valid amount of money
     * @param value a double
     * @return true if positive and <=2 decimal places, false otherwise
     */
    public static boolean isAmountValid(double value){
        if(value<=0){
            return false;
        }else if(value != Math.round(value*100)/100.0){
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email){
        //if email is empty
        if (email.length() ==0){
            return false;
        }
        char first = email.charAt(0);
        char last = email.charAt(email.length()-1);
        int atSymbol = email.indexOf('@');
        boolean findPeriod = false;

        //PREFIX TESTS

        //if there is no "@"
        if (atSymbol == -1 || email.indexOf('@') == -1){
            return false;
        }
        //else if the character before "@" is "-" or "."
        else if (email.charAt(atSymbol - 1) == '-' || email.charAt(atSymbol - 1) == '.' || email.charAt(atSymbol - 1) == '_'){
            return false;
        }
        //else if the first character in the address is "."
        else if (first == '.' || first == '-' || first == '_'){
            return false;
        }
        //else if the character before the last character is "."
        else if (email.charAt(email.length() - 2) == '.'){
            return false;
        }
        else {
            //go through each character in the email before the "@" symbol
            for (int x=0; x < atSymbol; x++) {
                //if there are two ".", "-", or "_" in a row
                if (email.charAt(x) == '.' && email.charAt(x + 1) == '.' || email.charAt(x) == '-' && email.charAt(x + 1) == '-'
                || email.charAt(x) == '_' && email.charAt(x + 1) == '_') {
                    return false;
                }
                //if there's a special character
                else if(!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@.-_".contains(email.charAt(x) + "")){
                    return false;
                }
            }

            //SUFFIX TESTS

            //for every character after the "@" symbol
            for (int x=atSymbol; x < email.length(); x++){
                int period = 0;

                //if the last character is "."
                if (last == '.' || last == '-' || last == '_'){
                    return false;
                }
                //else if there are two "." characters in a row
                else if (email.charAt(x) == '.' && email.charAt(x - 1) == '.' ||
                        email.charAt(x) == '-' && email.charAt(x - 1) == '-'){
                    return false;
                }
                //else if there has been a "." found somewhere in the suffix
                else if (email.charAt(x) == '.'){
                    findPeriod = true;
                    period += 1;
                }
                else if(!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@.-".contains(email.charAt(x) + "")){
                    return false;
                }

                else if (email.charAt(x) == '-' && email.charAt(x-1) == '@' || email.charAt(x) == '.' && email.charAt(x+1) == '-'){
                    return false;
                }

                //if more than one period in suffix
                if(period > 1){
                    return false;
                }
            }
            //if no "." is found
            if(!findPeriod){
                return false;
            }
            //else, email is valid
            else {
                return true;
            }
        }
    }
}


