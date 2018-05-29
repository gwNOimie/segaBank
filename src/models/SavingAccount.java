package models;

public class SavingAccount extends Account {
    private double interest;

    SavingAccount(double interest) {
        super();
        this.interest = interest;
    }

    double calculateInterest() {
        this.sold += this.sold * this.interest;
        return this.sold;
    }
}
