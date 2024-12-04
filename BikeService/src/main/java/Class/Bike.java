package Class;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bike implements Serializable {
    
    private Long id;
    private String model;
    private double price;
    
    private String condition;
    
    private Date dateAjout;
    private boolean available;
    private List<String> notes;
    private int rentalCounter;
    private List<GustaveUser> waitingList;
    
    
	private GustaveUser RentedBy ;
	
	
	

    
    public Bike() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GustaveUser getRentedBy() {
		return RentedBy;
	}

	public void setRentedBy(GustaveUser rentedBy) {
		RentedBy = rentedBy;
	}


    

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public int getRentalCounter() {
        return rentalCounter;
    }

    public void setRentalCounter(int rentalCounter) {
        this.rentalCounter = rentalCounter;
    }

    public List<GustaveUser> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<GustaveUser> waitingList) {
        this.waitingList = waitingList;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    // Constructor
    public Bike(Long id, String model, double price, String condition, boolean available, List<String> notes, int rentalCounter) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.condition = condition;
        this.available = available;
        this.notes = notes != null ? notes : new ArrayList<>(); // Ensure notes is never null
        this.rentalCounter = rentalCounter;
        this.waitingList = new ArrayList<>();  // Initialize the waiting list
        this.dateAjout = new Date();          // Set the dateAjout to the current date and time
        this.RentedBy=null;
    }
}
