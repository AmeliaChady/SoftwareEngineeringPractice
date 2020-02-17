package bank.ui;

import edu.ithaca.dragon.bank.AccountFrozenException;
import edu.ithaca.dragon.bank.BasicAPI;
import edu.ithaca.dragon.bank.CentralBank;
import edu.ithaca.dragon.bank.InsufficientFundsException;

import java.util.InputMismatchException;
import java.util.Scanner;


public class AtmUI {
    private enum AtmState{
        WAITING,
        LOGGEDIN,
        DEPOSIT,
        WITHDRAW,
        TRANSACTION
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
                        }else{
                            System.out.println("Error Logging In");
                        }
                    }
                    catch (AccountFrozenException e){
                        System.out.println("Account "+acctID+" is FROZEN. " +
                                "\n Contact Customer Services at " +
                                "\n ~~ 1-888-555-1212 ~~");
                    }
                    catch (NullPointerException e){
                        System.out.println("Error Logging In");
                    }
                    break;

                case LOGGEDIN:
                    System.out.println("\n\nAvailable Commands:" +
                            "\n logout, withdraw, deposit, transfer" +
                            "\n\nCurrent Balance: " + api.checkBalance(currentAccount));
                    System.out.print(" > ");
                    String command = user.nextLine().trim();

                    if(command.equalsIgnoreCase("logout")){
                        state = AtmState.WAITING;
                        currentAccount = null;
                    }
                    else if(command.equalsIgnoreCase("deposit")){
                        state = AtmState.DEPOSIT;
                    }
                    else if(command.equalsIgnoreCase("withdraw")){
                        state = AtmState.WITHDRAW;
                    }
                    else if(command.equalsIgnoreCase("transfer")){
                        state = AtmState.TRANSACTION;
                    }
                    else{
                        System.out.println("Invalid Command");
                    }
                    break;

                case DEPOSIT:
                    System.out.print("Amount to Deposit: ");
                    try {
                        double amount = user.nextDouble();
                        api.deposit(currentAccount, amount);
                        state = AtmState.LOGGEDIN;
                    }
                    catch (AccountFrozenException e){
                        System.out.println("Account "+currentAccount+" is FROZEN. " +
                                "\n Contact Customer Services at " +
                                "\n ~~ 1-888-555-1212 ~~");
                        currentAccount = null;
                        state = AtmState.WAITING;
                    }
                    catch (Exception e){
                        System.out.println("Invalid Amount");
                        state = AtmState.LOGGEDIN;
                    }
                    user.nextLine();
                    break;

                case WITHDRAW:
                    System.out.print("Amount to Withdraw: ");
                    try {
                        double amount = user.nextDouble();
                        api.withdraw(currentAccount, amount);
                        state = AtmState.LOGGEDIN;
                    }
                    catch (AccountFrozenException e){
                        System.out.println("Account "+currentAccount+" is FROZEN. " +
                                "\n Contact Customer Services at " +
                                "\n ~~ 1-888-555-1212 ~~");
                        currentAccount = null;
                        state = AtmState.WAITING;
                    }
                    catch (InsufficientFundsException e){
                        System.out.println("Not Enough Funds");
                        state = AtmState.LOGGEDIN;
                    }
                    catch (Exception e){
                        System.out.println("Invalid Amount");
                        state = AtmState.LOGGEDIN;
                    }
                    user.nextLine();
                    break;

                case TRANSACTION:

                    try {
                        System.out.print("Amount to Transfer: ");
                        double amount = user.nextDouble();
                        user.nextLine();
                        System.out.print("Account to Transfer to: ");
                        String otherAcctID = user.nextLine().trim();
                        api.transfer(currentAccount, otherAcctID, amount);
                        state = AtmState.LOGGEDIN;

                    }
                    catch (InsufficientFundsException e){
                        System.out.println("Not Enough Funds");
                        state = AtmState.LOGGEDIN;
                    }
                    catch (AccountFrozenException e){
                        if(e.getMessage().equalsIgnoreCase("Cannot transfer from frozen account")){
                            System.out.println("Account "+currentAccount+" is FROZEN. " +
                                    "\n Contact Customer Services at " +
                                    "\n ~~ 1-888-555-1212 ~~");
                            currentAccount = null;
                            state = AtmState.WAITING;
                        }else{
                            System.out.println("Unable to Transfer to Frozen Account");
                            state = AtmState.LOGGEDIN;
                        }
                    }
                    catch (IllegalArgumentException e){
                        if(e.getMessage().equalsIgnoreCase("ERROR: Invalid amount")){
                            System.out.println("Invalid Amount");
                        }
                        else if(e.getMessage().equalsIgnoreCase("ERROR: Must pass bank account")){
                            System.out.println("Invalid Account");
                        }
                        else{
                            System.out.println("Unknown Error: \n" + e.getMessage());
                        }
                        state = AtmState.LOGGEDIN;
                    }
                    break;
            }
        }
    }
}
