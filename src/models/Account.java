package models;

import store.StoreService;

public abstract class Account {
    protected int code;
    protected double sold;

    Account() {
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
        return String.valueOf(this.code).concat(" : ").concat(String.valueOf(this.sold));
    }
}
