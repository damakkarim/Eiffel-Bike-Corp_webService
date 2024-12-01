package Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Class.Bike;
import Class.GustaveUser;

public class BikeRepo {
	private static Map<Long, Bike> bikes = new HashMap<>();

    static {
    	// Creating a bike with a list of notes
    	Bike bike1 = new Bike(1L, "Model A", 200.0, "poor", true, new ArrayList<>(List.of("-First bike", "-Needs repair", "-Popular model")), 5);
    	Bike bike2 = new Bike(2L, "Model B", 150.0, "Used", false, new ArrayList<>(List.of("-Second bike", "-Recently serviced", "-Good condition")), 0);


        bikes.put(1L, bike1);
        bikes.put(2L, bike2);
        
    }

    public static void ajouter(Bike bike) {
        if (!bikes.containsKey(bike.getId())) {
            bikes.put(bike.getId(), bike);
        } else {
            System.out.println("Un vélo avec cet ID existe déjà.");
        }
    }

    
    
    
    public static Map<Long, Bike> afficherTous() {
        return bikes;
    }

    public static Bike afficherParId(Long id) {
                return bikes.get(id);
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

    public static void retourner(Long bikeId, String notes) {
        Bike bike = bikes.get(bikeId);

        if (bike != null && !bike.isAvailable()) { // Vérifie si le vélo est non disponible
            bike.setAvailable(true); // Marque le vélo comme disponible

            if (notes != null && !notes.isEmpty()) { // Vérifie si des notes sont fournies
                // Si le vélo a déjà des notes, on ajoute la nouvelle note à la liste
                if (bike.getNotes() != null) {
                    bike.getNotes().add(notes); // Ajoute la nouvelle note à la liste existante
                } else {
                    List<String> newNotes = new ArrayList<>();
                    newNotes.add(notes); // Crée une nouvelle liste de notes si aucune n'existait
                    bike.setNotes(newNotes); // Associe la nouvelle liste de notes au vélo
                }
            }
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
    
    
    
}
