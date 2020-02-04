package edu.ithaca.dragon.bank;

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
        return false;
    }
}
