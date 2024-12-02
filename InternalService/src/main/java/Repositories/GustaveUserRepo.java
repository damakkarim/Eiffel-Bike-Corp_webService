package Repositories;

import java.util.HashMap;
import java.util.Map;

import Class.GustaveUser;
import Class.Role;
import Services.IncorrectPasswordException;

public class GustaveUserRepo {
	
	private static Map<Long, GustaveUser> users = new HashMap<>();
    private static long userIdCounter = 3L;

    static {
        // Initialisation des utilisateurs
        GustaveUser user1 = new GustaveUser(1L, "Karim","dammakkarim@gmail.com", "password123", Role.STUDENT);
        GustaveUser user2 = new GustaveUser(2L, "Bob", "bob@univ-eiffel.fr", "password456", Role.EMPLOYEE);
        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);
    }


    // Récupérer tous les utilisateurs
    public static Map<Long, GustaveUser> getAll() {
        return users;
    }

    // Récupérer un utilisateur par son email
    public static GustaveUser getUserByEmail(String email) {
        for (GustaveUser user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    // Enregistrer un nouvel utilisateur
    public static GustaveUser register(GustaveUser uu) {
        // Vérifier si l'email est déjà pris
        for (GustaveUser user : users.values()) {
            if (user.getEmail().equals(uu.getEmail())) {
                System.out.println("Email déjà enregistré.");
                return uu;  // Retourner l'utilisateur existant si l'email est déjà enregistré
            }
        }
        // Créer un nouvel utilisateur avec un ID unique
        GustaveUser newUser = new GustaveUser(userIdCounter++, uu.getName(), uu.getEmail(), uu.getPassword(), uu.getRole());
        users.put(newUser.getId(), newUser);  // Ajouter l'utilisateur à la base de données
        return newUser;
    }
    
    

    
    
 // Se connecter (vérification de l'email et du mot de passe)
    public static GustaveUser signIn(String email, String password) throws IncorrectPasswordException {
        for (GustaveUser user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                if (user.getPassword().equals(password)) {
                    return user;  // Retourner l'utilisateur si les informations sont correctes
                } else {
                    throw new IncorrectPasswordException("Incorrect email or password");
                }
            }
        }
        throw new IllegalArgumentException("User Not found.");
    }

    
    
    public static GustaveUser save(GustaveUser user) {
        if (user.getId() == null) {
            user.setId(++userIdCounter);
        }
        users.put(user.getId(), user);
        return user;
    }

    public static GustaveUser getUserById(Long userId) {
    	
    	
    		
        	return users.get(userId);

       
    }

}
