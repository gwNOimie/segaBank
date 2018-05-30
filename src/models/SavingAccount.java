package models;

public class SavingAccount extends Account {
    private double interest;

    public SavingAccount(double interest) {
        super();
        this.interest = interest;
        this.type = "saving";
    }

    public double calculateInterest() {
        this.sold += this.sold * this.interest;
        return this.sold;
    }
}
