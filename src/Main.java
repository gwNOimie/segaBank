import models.Account;
import models.PayAccount;
import models.SavingAccount;
import models.SimpleAccount;
import store.StoreService;

import java.io.*;
import java.util.*;

public class Main {
    private final static String BACKUP_FILE_PATH = "/home/gwen/tmp/backup.bin";
    private static int accountCode = -1;
    private static Account currentAccount;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to SegaBank");
        File backup = new File(BACKUP_FILE_PATH);
        if (backup.isFile()) {
            loadBackup();
        } else {
            StoreService.accounts = new HashMap<Integer, Account>();
            StoreService.lastCode = 0;
        }
        askAccountCode();
    }

    private static void loadBackup() {
        System.out.println("Do you want to load last backup ? (type yes or no)");
        String answer;
        answer = sc.nextLine();
        while (!answer.equals(new String("yes")) && !answer.equals(new String("no"))) {
            System.out.println("Please type yes or no !");
            answer = sc.nextLine();
        }
        if (answer.equals(new String("yes"))) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(BACKUP_FILE_PATH);
                ObjectInputStream ois = new ObjectInputStream(fis);
                StoreService.accounts = (HashMap<Integer, Account>) ois.readObject();
                StoreService.lastCode = (int) ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            StoreService.accounts = new HashMap<Integer, Account>();
            StoreService.lastCode = 0;
        }
    }

    private static void askAccountCode() {
        System.out.println("Please enter account code : (0 to create)");
        try {
            accountCode = new Integer(sc.nextLine());
        } catch(Exception e) {
            System.out.println("Code must be an integer");
            accountCode = -1;
        }
        if (accountCode == 0) {
            createAccount();
        } else if (StoreService.accounts.containsKey(accountCode)) {
            currentAccount = StoreService.accounts.get(accountCode);
            promptMenu();
        } else {
            System.out.println("Wrong code.");
            askAccountCode();
        }
    }

    private static void createAccount() {
        int accountType = -1;
            System.out.println("1 : Simple");
            System.out.println("2 : Pay");
            System.out.println("3 : Saving");
            System.out.println("0 : Leave");

            System.out.print("Make your choice : ");
            try {
                accountType = new Integer(sc.nextLine());
            } catch(Exception e) {
                System.out.println("Choice must be an integer");
                accountType = -1;
            }
            switch (accountType) {
                case 1:
                    double overdraft = askAmount("overdraft");
                    SimpleAccount newSimpleAccount = new SimpleAccount(overdraft);
                    StoreService.accounts.put(newSimpleAccount.getCode(), newSimpleAccount);
                    saveBackup();
                    askAccountCode();
                    break;
                case 2:
                    PayAccount newPayAccount = new PayAccount();
                    StoreService.accounts.put(newPayAccount.getCode(), newPayAccount);
                    saveBackup();
                    askAccountCode();
                    break;
                case 3:
                    double interest = askAmount("interest");
                    SavingAccount newSavingAccount = new SavingAccount(interest);
                    StoreService.accounts.put(newSavingAccount.getCode(), newSavingAccount);
                    saveBackup();
                    askAccountCode();
                    break;
                default:
                    System.out.println("Choice is not available !");
                    createAccount();
                    break;
            }
        }

    private static double askAmount(String askedAmountName) {
        double amount = -1;
        try {
            System.out.print("What amount do you want to set for ".concat(askedAmountName).concat(" ? "));
            amount = sc.nextDouble();
        } catch (Exception e) {
            System.out.println("Must be a decimal");
            amount = askAmount(askedAmountName);
        }
        if (amount < 0) {
            amount = askAmount(askedAmountName);
        }
        return amount;
    }

    private static void saveBackup() {
        try {
            FileOutputStream fos = new FileOutputStream(BACKUP_FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(StoreService.accounts);
            oos.writeObject(StoreService.lastCode);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void promptMenu() {
        int menuChoice;
        if (currentAccount != null) {
            System.out.println("1 : Print sold");
            System.out.println("2 : Add cash");
            System.out.println("3 : Get cash");
            if (currentAccount.type == "saving") {
                System.out.println("4 : Get interest");
            }
            System.out.println("0 : Leave");

            System.out.print("Make your choice : ");
            try {
                menuChoice = new Integer(sc.nextLine());
            } catch(Exception e) {
                System.out.println("Choice must be an integer");
                menuChoice = -1;
            }
            switch (menuChoice) {
                case 0:
                    currentAccount = null;
                    accountCode = 0;
                    askAccountCode();
                    break;
                case 1:
                    System.out.println(currentAccount);
                    promptMenu();
                    break;
                case 2:
                    System.out.print("Enter amount : ");
                    long amountIn = sc.nextLong();
                    System.out.println("New sold : ".concat(String.valueOf(currentAccount.putCash(amountIn))));
                    promptMenu();
                    break;
                case 3:
                    System.out.print("Enter amount : ");
                    long amountOut = sc.nextLong();
                    System.out.println("New sold : ".concat(String.valueOf(currentAccount.getCash(amountOut))));
                    promptMenu();
                    break;
                case 4:
                    if (currentAccount.type == "saving") {
                        System.out.println("New sold : ".concat(String.valueOf(((SavingAccount)currentAccount).calculateInterest())));
                    } else {
                        System.out.println("Wrong choice !");
                    }
                    promptMenu();
                    break;
                default:
                    System.out.println("Choice is not available !");
                    promptMenu();
                    break;
            }
        } else {
            askAccountCode();
        }
    }
}
