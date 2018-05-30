package store;

import models.Account;

import java.io.Serializable;
import java.util.HashMap;

public class StoreService implements Serializable {
    public static int lastCode;
    public static HashMap<Integer, Account> accounts;
}
