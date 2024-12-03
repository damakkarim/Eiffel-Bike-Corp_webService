package Class;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class UserAccount implements Serializable{
	
    private Long id;
    private String userName;
    private double balance;
   
    private String  cardnumber;
    private LocalDate ExpirationDate;
    private Long cvv;
    
    
    

    
    
    




	public UserAccount(Long id, String userName, double balance, String cardnumber, LocalDate expirationDate, Long cvv) {
		super();
		this.id = id;
		this.userName = userName;
		this.balance = balance;
		this.cardnumber = cardnumber;
		this.ExpirationDate = expirationDate;
		this.cvv = cvv;
	}
	
	
	
	
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public LocalDate getExpirationDate() {
		return ExpirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		ExpirationDate = expirationDate;
	}
	public Long getCvv() {
		return cvv;
	}
	public void setCvv(Long cvv) {
		this.cvv = cvv;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public UserAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userName=" + userName + ", balance=" + balance + "]";
	}
    
    
    
}