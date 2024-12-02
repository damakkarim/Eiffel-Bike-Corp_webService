package Class;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class ExternalUser implements Serializable  {
	private static final long serialVersionUID = 1L;

	private Long id;
    private String name;
    private String email;
    private String password;
    private UserAccount userAccount; // Liaison avec UserAccount

    
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "ExternalUser [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	public ExternalUser(Long id, String name, String email, String password,UserAccount userAccount ) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userAccount=userAccount;
	}
	public ExternalUser() {
		super();
	}
	
	

}
