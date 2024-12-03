package Services;

import Class.UserAccount;
import Class.PaymentRequest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Class.PaymentRequest;
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
    @Path("/save") 
    @Consumes("application/json") 
    @Produces("application/json")
    public Response saveAccount(UserAccount account) { 
    	AccountRepository.saveAccount(account); 
    	return Response.ok("Account saved successfully!").build();
    	}  

    
    
    
    
    
   
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        System.out.println("Fetching all accounts...");
        Map<Long, UserAccount> accounts = accountRepo.getAll();
        System.out.println("Accounts found: " + accounts.size());
        return Response.ok(accounts).build();
    }
    
    

    
    @POST
    @Path("/processPayment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processPayment(PaymentRequest paymentRequest) {
        Long accountId = paymentRequest.getAccountId();
        double amount = paymentRequest.getAmount();

        // Vérifier si les fonds sont suffisants
        if (!checkFunds(accountId, amount)) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Insufficient funds")
                           .build();
        }

        // Trouver le compte de l'utilisateur
        UserAccount account = accountRepo.findById(accountId);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Account not found")
                           .build();
        }

        // Créer la transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setSuccessful(true); // Supposons que le paiement est réussi

        // Débiter le compte
        account.setBalance(account.getBalance() - amount);

        // Enregistrer la transaction dans le repository
        transactionRepo.save(transaction);

        // Répondre avec les informations de la transaction
        return Response.ok(transaction).build();
    }


    

    
    
}
