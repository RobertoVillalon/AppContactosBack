package cl.beto.model;

import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="userID") @Getter @Setter
	private int userID;
	
	@Column(name="username") @Getter @Setter
	private String username;
	
	@Column(name="email") @Getter @Setter
	private String email;
	
	@Column(name="password") @Getter @Setter
	private String password;
	
	@Column(name="userCareer") @Getter @Setter
	private String userCareer;
	
	@Column(name="phone") @Getter @Setter
	private String phone;
	
	@Column(name="address") @Getter @Setter
	private String address;
	
	@Column(name="personalDescription") @Getter @Setter
	private String personalDescription;
	
	@Column(name="socialMedias") @Getter @Setter
	private String socialMedias;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	private List<Contact> contactos;
	
	public User() {
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", userCareer=" + userCareer + ", phone=" + phone + ", address=" + address + ", personalDescription="
				+ personalDescription + ", socialMedias=" + socialMedias + ", contactos=" + contactos + "]";
	}
	
}
