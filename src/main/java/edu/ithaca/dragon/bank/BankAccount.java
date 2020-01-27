package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws IllegalArgumentException when the amount is too large or negative
     */
    public void withdraw (double amount)  {
        //if user tries to withdraw negative amount
        if (amount < 0){
            throw new IllegalArgumentException("ERROR: Cannot withdraw from negative balance.");
        }

        //if amount trying to withdraw is greater than balance
        if (balance < amount){
            throw new IllegalArgumentException("ERROR: Not enough funds to withdraw that amount.");

        }
        balance -= amount;

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
        int atInstances = 0;

        //PREFIX TESTS

        //if there is no "@"
        if (atSymbol == -1){
            return false;
        }
        if (email.indexOf('@') == -1){
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
                //if there are two "." in a row
                if (email.charAt(x) == '.' && email.charAt(x + 1) == '.' || email.charAt(x) == '-' && email.charAt(x + 1) == '-'
                || email.charAt(x) == '_' && email.charAt(x + 1) == '_') {
                    return false;
                }
                //if there's a special character
                else if (email.charAt(x) == '#' || email.charAt(x) == ' ' || email.charAt(x) == ',' || email.charAt(x) == '<'
                || email.charAt(x) == '>' || email.charAt(x) == '$' || email.charAt(x) == '/' || email.charAt(x) == '|'
                || email.charAt(x) == '~' || email.charAt(x) == '=' || email.charAt(x) == ';' || email.charAt(x) == ':'
                || email.charAt(x) == '[' || email.charAt(x) == ']' || email.charAt(x) == '%' || email.charAt(x) == '?'
                || email.charAt(x) == '"' || email.charAt(x) == '{' || email.charAt(x) == '}' || email.charAt(x) == '!'
                || email.charAt(x) == '@' || email.charAt(x) == '^' || email.charAt(x) == '&' || email.charAt(x) == '*'
                || email.charAt(x) == '+' ){
                    return false;
                }

            }

            //SUFFIX TESTS

            //for every character after the "@" symbol
            for (int x=atSymbol; x < email.length(); x++){
                int period = 0;
                //if there is a "#" character
                if (email.charAt(x) == '#'){
                    return false;
                }
                //if the last character is "."
                else if (last == '.'){
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
                else if(!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@.".contains(String.valueOf(x))){
                    return false;
                }
                else if (email.charAt(x) == ' '){
                    return false;
                }
                else if (email.charAt(x) == '.' && email.charAt(x+1) == '-')
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


