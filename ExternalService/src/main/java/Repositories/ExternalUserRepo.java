package Repositories;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import Class.ExternalUser;
import Class.UserAccount;
import Services.IncorrectPasswordException;
import Class.ExternalUser;

public class ExternalUserRepo {
	
	private static Map<Long, ExternalUser> users = new HashMap<>();
    private static long userIdCounter = 3L;

    
    static {
    	// Initialisation des utilisateurs avec UserAccount
    	UserAccount account1 = new UserAccount(1L, "siwar_account", 1000.0, "1234567890123456", LocalDate.of(2025, 12, 31), 123L);
    	ExternalUser user1 = new ExternalUser(1L, "siwar", "siwar@university.com", "password123", account1);

    	UserAccount account2 = new UserAccount(2L, "ahmed_account", 2000.0, "9876543210987654", LocalDate.of(2026, 6, 30), 456L);
    	ExternalUser user2 = new ExternalUser(2L, "ahmed", "ahmed@university.com", "password456", account2);


    	// Stockage des utilisateurs dans la collection
    	users.put(user1.getId(), user1);
    	users.put(user2.getId(), user2);

    }


    
 // Récupérer tous les utilisateurs
    public static Map<Long, ExternalUser> getAll() {
        return users;
    }

    // Récupérer un utilisateur par son email
    public static ExternalUser getUserByEmail(String email) {
        for (ExternalUser user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    



    public static ExternalUser register(ExternalUser uu) {
        // Afficher les données de l'utilisateur pour le débogage
        System.out.println("Received registration request: " + uu);

        // Vérifier si l'email est déjà pris
        for (ExternalUser user : users.values()) {
            if (user.getEmail().equals(uu.getEmail())) {
                System.out.println("Email already registered: " + uu.getEmail());
                return null; // Retourner null si l'email est déjà pris, gérer cela côté appelant
            }
        }

        // Vérification de l'objet UserAccount
        if (uu.getUserAccount() == null) {
            System.out.println("User account information is required.");
            return null; // Retourner null si l'objet UserAccount est manquant
        }

        // Vérification des champs requis pour UserAccount
        if (uu.getUserAccount().getUserName() == null || uu.getUserAccount().getCardnumber() == null ||
            uu.getUserAccount().getExpirationDate() == null || uu.getUserAccount().getCvv() == null) {
            System.out.println("Incomplete user account details.");
            return null; // Retourner null si les informations du compte sont incomplètes
        }
        



        // Créer un nouvel utilisateur avec un ID unique
        ExternalUser newUser = new ExternalUser(
        	userIdCounter, // Générer un ID unique
            uu.getName(),
            uu.getEmail(),
            uu.getPassword(),
            uu.getUserAccount() // Associer l'objet UserAccount à l'utilisateur
        );

        
        // Ajouter l'utilisateur à la base de données
        users.put(newUser.getId(), newUser);
        
        

        // Log et retour du nouvel utilisateur inscrit
        System.out.println("User registered successfully: " + newUser.getEmail());

        return newUser;
    }


    
 // Se connecter (vérification de l'email et du mot de passe)
    public static ExternalUser signIn(String email, String password) throws IncorrectPasswordException {
        for (ExternalUser ExternalUser : users.values()) {
            if (ExternalUser.getEmail().equalsIgnoreCase(email)) {
                if (ExternalUser.getPassword().equals(password)) {
                    return ExternalUser;  // Retourner l'utilisateur si les informations sont correctes
                } else {
                    throw new IncorrectPasswordException("Incorrect email or password");
                }
            }
        }
        throw new IllegalArgumentException("User Not found.");
    }

    
    
    public static ExternalUser save(ExternalUser user) {
        if (user.getId() == null) {
            user.setId(userIdCounter);
        }
        users.put(user.getId(), user);
        return user;
    }

    
    
    public static ExternalUser getUserById(Long userId) {
    	
    	
    		
        	return users.get(userId);

       
    }
   
    
    
   
}
