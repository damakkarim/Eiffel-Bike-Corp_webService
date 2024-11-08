package Classes;
import java.util.List;

public class Bike {
	private Long id;
    private String model;
    private double price;
    private String condition;
    private boolean available;
    private List<ExternalUser> waitingList;
    private long rentalCounter;
    private String notes;
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
	public List<ExternalUser> getWaitingList() {
		return waitingList;
	}
	public void setWaitingList(List<ExternalUser> waitingList) {
		this.waitingList = waitingList;
	}
	public long getRentalCounter() {
		return rentalCounter;
	}
	public void setRentalCounter(long rentalCounter) {
		this.rentalCounter = rentalCounter;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Bike(Long id, String model, double price, String condition, boolean available,
			List<ExternalUser> waitingList, long rentalCounter, String notes) {
		super();
		this.id = id;
		this.model = model;
		this.price = price;
		this.condition = condition;
		this.available = available;
		this.waitingList = waitingList;
		this.rentalCounter = rentalCounter;
		this.notes = notes;
	}
	public Bike(String id2, String model2, double price2, String condition2, boolean b) {
		// TODO Auto-generated constructor stub
	}
	

}
