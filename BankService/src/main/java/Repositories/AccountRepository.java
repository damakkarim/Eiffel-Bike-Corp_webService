package Repositories;

import java.util.HashMap;
import java.util.Map;

import Class.UserAccount;

public class AccountRepository {
	
    private static Map<Long, UserAccount> accounts = new HashMap<>();

    
    static {
        accounts.put(1L, new UserAccount(1L, "Siwar", 55584848));
        accounts.put(2L, new UserAccount(2L, "Ahmed", 511511));
    }
    
    
    // Method to find a UserAccount by ID
    public UserAccount findById(Long id) {
        return accounts.get(id); // Return the UserAccount or null if not found
    }
    
    
    // Save a UserAccount (add or update)
    public void save(UserAccount account) {
        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("Account or Account ID cannot be null");
        }

        // Add or update the account in the repository
        accounts.put(account.getId(), account);
        System.out.println("Account saved: " + account);
    }

}
