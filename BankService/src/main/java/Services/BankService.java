package Services;

import Class.UserAccount;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Class.Transaction;
import Repositories.AccountRepository;
import Repositories.TransactionRepository;



@Path("/Bank")
public class BankService {

    private AccountRepository accountRepo = new AccountRepository();
    private TransactionRepository transactionRepo = new TransactionRepository();

    
    
    
    // Check if the account has enough funds for a given amount
    public boolean checkFunds(Long accountId, double amount) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }

        UserAccount account = accountRepo.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return account.getBalance() >= amount;
    }
    
    
    
    
    
    
    @POST
    @Path("/processPayment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    // Process payment by deducting the amount and creating a transaction
    public boolean processPayment(UserAccount account, double amount) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }

        // Check if sufficient funds are available
        if (checkFunds(account.getId(), amount)) {
            // Deduct the amount from the account's balance
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);

            // Save the updated account
            accountRepo.save(account);

            // Create a successful transaction
            Transaction transaction = new Transaction(account, amount, true);
            transactionRepo.save(transaction);

            System.out.println("Payment processed successfully. Transaction: " + transaction);
            return true;
        } else {
            // Create a failed transaction
            Transaction transaction = new Transaction(account, amount, false);
            transactionRepo.save(transaction);

            System.out.println("Payment failed due to insufficient funds. Transaction: " + transaction);
            return false;
        }
    }
}
