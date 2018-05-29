package models;

public class SimpleAccount extends Account {
    private int overdraft;
    SimpleAccount(int overdraft) {
        super();
        this.overdraft = overdraft;
    }

    @Override
    public double getCash(double amount) {
        if (this.sold - amount > this.overdraft) {
            return super.getCash(amount);
        } else {
            System.out.println("You don't have enough cash on your account to get some...");
            return this.sold;
        }
    }
}
