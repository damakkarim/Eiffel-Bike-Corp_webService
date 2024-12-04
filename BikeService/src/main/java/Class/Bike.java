package Class;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    private GustaveUser rentedBy;

    // Getters and Setters
    public GustaveUser getRentedBy() {
        return rentedBy;
    }
    
    
    

    public Bike() {
		super();
		// TODO Auto-generated constructor stub
	}




	public void setRentedBy(GustaveUser rentedBy) {
        this.rentedBy = rentedBy;
    }

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
    public Bike(Long id, String model, double price, boolean available, List<String> notes, int rentalCounter) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.available = available;
        this.notes = notes != null ? notes : new ArrayList<>(); // Ensure notes is never null
        this.rentalCounter = rentalCounter;
        this.waitingList = new ArrayList<>();  // Initialize the waiting list
        this.dateAjout = new Date();          // Set the dateAjout to the current date and time
        this.rentedBy = null;

        // Call the FastAPI service to determine the condition based on all notes
        if (!this.notes.isEmpty()) {
            this.condition = fetchConditionFromApi(this.notes);
        } else {
            this.condition = "Unknown"; // Default condition if no notes are provided
        }
    }

    // Method to fetch condition from FastAPI
    private String fetchConditionFromApi(List<String> notes) {
        try {
            // Define the FastAPI endpoint
            String apiEndpoint = "http://127.0.0.1:8000/predict-condition";

            // Combine all notes into a single text
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(new Object() {
                public final List<String> descriptions = notes;
            });

            // Create an HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiEndpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send the request and get a response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            if (jsonResponse.has("predicted_condition")) {
                return jsonResponse.get("predicted_condition").asText();
            } else {
                return "Unknown"; // Default condition if API response is unexpected
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching condition"; // Handle any errors gracefully
        }
    }
}
