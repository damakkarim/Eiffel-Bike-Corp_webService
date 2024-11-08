package Repositories;

import java.util.HashMap;
import java.util.Map;

import Classes.Bike;

public class BikeRepo {
	private static Map<Long, Bike> bikes=new HashMap<Long, Bike>();
	
	
	
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
    
}
