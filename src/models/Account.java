package models;

import store.StoreService;

import java.io.Serializable;

public abstract class Account implements Serializable {
    public String type;
    protected int code;
    protected double sold;

    Account() {
        StoreService.lastCode++;
        this.code = StoreService.lastCode;
        this.sold = 0;
    }

    public double putCash(double amount) {
        this.sold += amount;
        return this.sold;
    };

    public double getCash(double amount) {
        this.sold -= amount;
        return this.sold;
    }

    public String toString() {
        return "Code : ".concat(String.valueOf(this.code)).concat(" / Sold : ").concat(String.valueOf(this.sold));
    }

    public int getCode() {
        return this.code;
    }
}
