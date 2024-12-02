package Repositories;

import java.util.HashMap;
import java.util.Map;

import Class.ExternalUser;
import Services.IncorrectPasswordException;
import Class.ExternalUser;

public class ExternalUserRepo {
	
	private static Map<Long, ExternalUser> users = new HashMap<>();
    private static long userIdCounter = 3L;

    
    static {
        // Initialisation des utilisateurs
    	ExternalUser user1 = new ExternalUser(1L, "siwar", "siwar@university.com", "password123");
    	ExternalUser user2 = new ExternalUser(2L, "ahmed", "ahmed@university.com", "password456");
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

    // Enregistrer un nouvel utilisateur
    public static ExternalUser register(ExternalUser uu) {
        // Vérifier si l'email est déjà pris
        for (ExternalUser user : users.values()) {
            if (user.getEmail().equals(uu.getEmail())) {
                System.out.println("Email déjà enregistré.");
                return uu;  // Retourner l'utilisateur existant si l'email est déjà enregistré
            }
        }
        // Créer un nouvel utilisateur avec un ID unique
        ExternalUser newUser = new ExternalUser(userIdCounter++, uu.getName(), uu.getEmail(), uu.getPassword());
        users.put(newUser.getId(), newUser);  // Ajouter l'utilisateur à la base de données
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
            user.setId(++userIdCounter);
        }
        users.put(user.getId(), user);
        return user;
    }

    
    
    public static ExternalUser getUserById(Long userId) {
    	
    	
    		
        	return users.get(userId);

       
    }
    

    
}
