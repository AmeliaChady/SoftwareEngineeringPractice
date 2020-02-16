package bank.ui;

import edu.ithaca.dragon.bank.AccountFrozenException;
import edu.ithaca.dragon.bank.BasicAPI;
import edu.ithaca.dragon.bank.CentralBank;

import java.util.Scanner;


public class AtmUI {
    private enum AtmState{
        WAITING,
        LOGGEDIN,
        DEPOSIT_INPUT,
        WITHDRAW_INPUT,
        TRANSACTION_INPUT
    }

    public static void main(String[] args) {
        BasicAPI api = CentralBank.testBank();
        AtmState state = AtmState.WAITING;
        Scanner user = new Scanner(System.in);
        String currentAccount = null;

        while(true){
            switch (state){
                case WAITING:
                    System.out.print("Account ID: ");
                    String acctID = user.nextLine();
                    System.out.print("Password: ");
                    String pass = user.nextLine();

                    try {
                        if (api.confirmCredentials(acctID.trim(), pass.trim())) {
                            currentAccount = acctID;
                            state = AtmState.LOGGEDIN;
                        }
                    }
                    catch (AccountFrozenException e){
                        System.out.println("Account "+acctID+" is FROZEN. " +
                                "\n Contact Customer Services at " +
                                "\n ~~ 1-888-555-1212 ~~");
                    }
                    break;

                case LOGGEDIN:
                    System.out.println("HERE");

                case DEPOSIT_INPUT:

                case WITHDRAW_INPUT:

                case TRANSACTION_INPUT:
            }
        }
    }
}
