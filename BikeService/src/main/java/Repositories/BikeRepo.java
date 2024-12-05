package Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import Class.Bike;
import Class.EmailAlert;
import Class.GustaveUser;

public class BikeRepo {
	private static Map<Long, Bike> bikes = new HashMap<>();
	private   static Long BikeCounter=5L;

    static {
    	// Creating a bike with a list of notes
    	Bike bike1 = new Bike(1L, "Model A", 20.0, true, new ArrayList<>(List.of("-First bike", "-Needs repair", "-Popular model")), 5);
    	Bike bike2 = new Bike(2L, "Model B", 150.0, true, new ArrayList<>(List.of("-Second bike", "-Recently serviced", "-Good condition")), 4);
    	Bike bike3 = new Bike(3L, "Model C", 150.0, true, new ArrayList<>(List.of("Old","hard to ride it")), 0);
    	Bike bike4 = new Bike(4L, "Model D", 150.0, true, new ArrayList<>(List.of("Good Experience !")), 0);


        bikes.put(1L, bike1);
        bikes.put(2L, bike2);
        bikes.put(3L, bike3);

        bikes.put(4L, bike4);

        
    }
    public static void ajouter(Bike bike) {
        if (!bikes.containsKey(bike.getId())) {
            bike.setId(BikeCounter);
            bike.setAvailable(true);

            bikes.put(bike.getId(), bike);
            
            BikeCounter++;
        } else {
            System.out.println("A bike with this ID already exists.");
        }
    }
    
    
    public static Map<Long, Bike> afficherTous() {
        return bikes;
    }

    public static Bike afficherParId(Long id) {
                return bikes.get(id);
    }
    
    
    
    // Nouvelle méthode pour récupérer un vélo loué par un utilisateur
    public static Bike getRentedBikeByUser(Long userId) {
        return bikes.values().stream()
                .filter(bike -> bike.getRentedBy() != null && bike.getRentedBy().getId().equals(userId))
                .findFirst()
                .orElse(null); // Retourne null si aucun vélo n'est trouvé
    }

    
    
    public static boolean louer(Long bikeId,GustaveUser rentedby ) {
        Bike bike = bikes.get(bikeId);
        if (bike != null && bike.isAvailable()) {
            bike.setAvailable(false);
            bike.setRentalCounter(bike.getRentalCounter() + 1);
            bike.setRentedBy(rentedby);
            return true;
        }
        return false;
    }

    
    
    
    
    
    
    
    public static void retourner(Long bikeId, List<String> notes) {
        Bike bike = bikes.get(bikeId);

        if (bike != null && !bike.isAvailable()) { // Vérifie si le vélo est non disponible
            bike.setAvailable(true); // Marque le vélo comme disponible
            bike.setRentedBy(null); // Libère le vélo

            // Gestion des notes
            if (notes != null && !notes.isEmpty()) {
                if (bike.getNotes() == null) {
                    bike.setNotes(new ArrayList<>()); // Crée une nouvelle liste si elle est nulle
                }
                bike.getNotes().addAll(notes); // Ajoute toutes les nouvelles notes
            }

            // Gestion de la liste d'attente
            if (bike.getWaitingList() != null && !bike.getWaitingList().isEmpty()) {
                GustaveUser headOfQueue = bike.getWaitingList().remove(0); // Retire la première personne de la liste
                sendEmailNotification(bike, headOfQueue); // Envoie un email à la personne suivante dans la file d'attente
            }

            System.out.println("Vélo retourné avec succès. Notes mises à jour et notifications envoyées si nécessaire.");
        } else {
            System.out.println("Le vélo est déjà disponible ou n'existe pas.");
        }
    }

    
    // Méthode utilitaire pour envoyer une notification par email
    private static void sendEmailNotification(Bike bike, GustaveUser user) {
        if (user != null) {
            String recipientEmail = user.getEmail();
            String subject = "Vélo disponible : " + bike.getModel();
            String body = "Bonjour " + user.getName() + ",\n\n" +
                          "Le vélo modèle " + bike.getModel() + " est maintenant disponible.\n" +
                          "Merci de nous contacter pour confirmer votre location.\n\n" +
                          "Cordialement,\nVotre équipe de gestion des vélos.";
            EmailAlert.sendEmail(recipientEmail, subject, body);
            System.out.println("Notification envoyée à : " + recipientEmail);
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    

    public static String ajouterALaListeDAttente(Long bikeId, GustaveUser user) {
        Bike bike = bikes.get(bikeId);
        if (bike == null) {
            return "VELO_INEXISTANT";
        }
        
        if (bike.isAvailable()) {
            return "VELO_DISPONIBLE";
        }
        
        // Initialiser la liste d'attente si elle est null
        if (bike.getWaitingList() == null) {
            bike.setWaitingList(new ArrayList<>());
        }
        
        // Vérification explicite de la présence de l'utilisateur
        for (GustaveUser existingUser : bike.getWaitingList()) {
        	System.out.print(existingUser.getId());
        	System.out.print(user.getId());
            if (existingUser.getId()==user.getId()) {
                return "UTILISATEUR_DEJA_DANS_LA_LISTE";
            }
        }
        
        // Ajouter l'utilisateur à la liste d'attente
        bike.getWaitingList().add(user);
        return "AJOUT_REUSSI";
    }

    public static boolean retirerDeLaListeDAttente(Long bikeId, Long userId) {
        Bike bike = bikes.get(bikeId);
        if (bike != null) {
            return bike.getWaitingList().removeIf(user -> user.getId().equals(userId));
        }
        return false;
    }

    
    public static double calculateRentalPrice(Bike bike) {
        double baseRate = 0.1; // Base rate: 10% of the initial price
        double depreciationRate = 0.01; // Depreciation per rental: 1% of the price
        double conditionMultiplier = getConditionMultiplier(bike);

        // Depreciate the base rental price
        double depreciation = bike.getRentalCounter() * depreciationRate * bike.getPrice();
        double rentalPrice = (bike.getPrice()* baseRate - depreciation) * conditionMultiplier;

        // Ensure the price doesn't go below a minimum threshold
        double minimumPrice = 5.0; // Minimum price in your currency
        return Math.max(rentalPrice, minimumPrice);
    }

    private static double getConditionMultiplier(Bike bike) {
        switch (bike.getCondition().toLowerCase()) {
            case "new":
                return 1.0; // No adjustment
            case "good":
                return 0.9; // 10% discount
            case "average":
                return 0.8; // 20% discount
            case "poor":
                return 0.7; // 30% discount
            default:
                return 1.0; // Default multiplier
        }
    }
    
    
    public static boolean removeBike(Long id) {
        if (bikes.containsKey(id)) {
            bikes.remove(id);
            return true;
        }
        return false; // Retourne false si l'ID n'existe pas
    }
    
    
}
