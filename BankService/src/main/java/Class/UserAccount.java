package Class;

import java.io.Serializable;

public class UserAccount implements Serializable{
    private Long id;
    private String userName;
    private double balance;
    
    
	public UserAccount(long l, String userName, double string) {
		super();
		this.id = l;
		this.userName = userName;
		this.balance = string;
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