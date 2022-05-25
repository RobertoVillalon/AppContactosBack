package cl.beto.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user")
@ToString @NoArgsConstructor
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
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "roleID"))
    @Getter @Setter
    private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	} 
    
}
