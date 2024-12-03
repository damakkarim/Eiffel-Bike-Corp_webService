package Class;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private UserAccount account;
    private double amount;
    private boolean isSuccessful;

    // Constructors
    public Transaction() {
    }

    public Transaction(UserAccount account, double amount, boolean isSuccessful) {
        this.account = account;
        this.amount = amount;
        this.isSuccessful = isSuccessful;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    // toString() for debugging
    @Override
    public String toString() {
        return "Transaction [id=" + id + ", account=" + account + ", amount=" + amount + ", isSuccessful=" + isSuccessful + "]";
    }
}
