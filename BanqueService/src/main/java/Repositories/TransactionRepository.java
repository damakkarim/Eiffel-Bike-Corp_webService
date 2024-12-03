package Repositories;

import Class.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    // Storage for transactions
    private static List<Transaction> transactions = new ArrayList<>();
    private static int transactionIdCounter = 1; // Auto-incrementing ID for transactions

    // Save a Transaction
    public void save(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        
        // Assign a unique ID if not already set
        if (transaction.getId() == 0) {
            transaction.setId(transactionIdCounter++);
        }
        
        // Add the transaction to the list
        transactions.add(transaction);
        System.out.println("Transaction saved: " + transaction);
    }

    // Get all Transactions
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions); // Return a copy to prevent external modification
    }

    // Find a Transaction by ID
    public Transaction findById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null; // Return null if not found
    }

    // Display all transactions (optional utility method for testing)
    public void displayAllTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
