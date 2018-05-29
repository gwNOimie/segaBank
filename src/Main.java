import models.Account;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Integer lastCode;
    private Map<Integer, Account> accounts;
    private int accountCode;
    private Account currentAccount;
    private static final Scanner sc = new Scanner(System.in);

    void Main() {
        System.out.println("Welcome to SegaBank");
        // TODO: Test if backup file exists before asking
        System.out.println("Do you want to load last backup ? (type yes or no)");
        String answer;
        answer = sc.nextLine();
        while (!answer.equals(new String("yes")) && !answer.equals(new String("no"))) {
            System.out.println("Please type yes or no !");
            answer = sc.nextLine();
        }
        if (answer.equals(new String("yes"))) {
            // TODO: load backup
        } else {
            // TODO: init empty array
        }
        // TODO: ask account code
        this.askAccountCode();
        // TODO: if account is correct, prompt menu depending on type (+contain leave entry)
        // TODO: act from choice
    }

    private void askAccountCode() {
        System.out.println("Please enter your account code and press enter");
        this.accountCode = new Integer(sc.nextInt());
        if (accounts.containsKey(this.accountCode)) {
            this.currentAccount = accounts.get(this.accountCode);
            this.promptMenu();
        } else {
            System.out.println("Wrong code. Please enter your account code and press enter");
        }
    }
}
