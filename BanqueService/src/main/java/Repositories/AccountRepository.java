package Repositories;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import Class.ExternalUser;
import Class.UserAccount;


public class AccountRepository {
	
	private static Map<Long, UserAccount> accounts = new HashMap<>();


    static {
    	// Initialisation des utilisateurs avec UserAccount
    	UserAccount account1 = new UserAccount(1L, "siwar_account", 1000.0, "1234567890123456", LocalDate.of(2025, 12, 31), 123L);

    	UserAccount account2 = new UserAccount(2L, "ahmed_account", 2000.0, "9876543210987654", LocalDate.of(2026, 6, 30), 456L);


    	// Stockage des utilisateurs dans la collection
    	accounts.put(account1.getId(), account1);
    	accounts.put(account2.getId(), account2);

    }

    // Method to find a UserAccount by ID
    public UserAccount findById(Long id) {
        return accounts.get(id); // Return the UserAccount or null if not found
    }
    
    
    
	public static void saveAccount(UserAccount account) { 
		accounts.put(account.getId(), account); 
		System.out.println("Account saved: " + account.getId()); }  

	
	
    
    // Save a UserAccount (add or update)
    public void save(UserAccount account) {
        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("Account or Account ID cannot be null");
        }

        // Add or update the account in the repository
        accounts.put(account.getId(), account);
        System.out.println("Account saved: " + account);
    }
    
 // Récupérer tous les utilisateurs
    public  Map<Long, UserAccount> getAll() {
        return accounts;
    }
    
    
    

}
