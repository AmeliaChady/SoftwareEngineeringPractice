package edu.ithaca.dragon.bank;

import java.util.regex.Pattern;

public class Utilities {

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

    /**
     * Takes a string and evaluates if it is a valid account id
     * account ids are 10 characters with the only allowed characters
     * being digits (0 through 9)
     * @param id the possible id
     * @return true if it is valid, otherwise false
     */
    public static boolean isAccountIDValid(String id){
        return Pattern.matches("\\d{10}", id) ;
    }

    public static boolean isPasswordValid(String password){
        if(password.length() < 5 || password.length() < 15){
            return false;
        }
        int numCount = 0;
        int specialCharCount = 0;
        int letterCount = 0;
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < password.length(); i++){
            if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 0){
                numCount+=1;
            }
            if(password.charAt(i) == '!' || password.charAt(i) == '@' || password.charAt(i) == '#' || password.charAt(i) == '$' ||
                    password.charAt(i) == '%' || password.charAt(i) == '&' || password.charAt(i) == '*'){
                specialCharCount+=1;
            }
            if (alphabet.contains(String.valueOf(password.charAt(i)))){
                letterCount+=1;
            }
        }
        if (letterCount <1 || numCount <1 || specialCharCount <1){
            return false;
        }
        else{return true;}
    }
}
