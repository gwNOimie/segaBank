package models;

public class PayAccount extends Account {
    @Override
    public double getCash(double amount) {
        this.sold = super.getCash(amount) - 0.05 * amount;
        return this.sold;
    }

    @Override
    public double putCash(double amount) {
        this.sold = super.putCash(amount) - 0.05 * amount;
        return this.sold;
    }
}
