package Repositories;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import Class.UserAccount;

public class AccountRepository {
	
	private static Map<Long, UserAccount> accounts = new HashMap<>();

	static {
	    accounts.put(1L, new UserAccount(1L, "JohnDoe", 1500.00, 1234567812345678L, LocalDate.of(2025, 12, 31), 123L));
	    accounts.put(2L, new UserAccount(2L, "JaneSmith", 2500.50, 9876543298765432L, LocalDate.of(2024, 11, 30), 456L));
	    accounts.put(3L, new UserAccount(3L, "BobBrown", 1000.75, 5555444433332222L, LocalDate.of(2026, 6, 15), 789L));
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
