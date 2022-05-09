package cl.beto.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contact")
public class Contact {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="RelationShipID") @Getter @Setter
	private int RelationShipID;

	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST}) @JoinColumn(name="userID") @Getter @Setter
	private User user;
	
	@Column(name="friendID") @Getter @Setter
	private int friendID;

	@Override
	public String toString() {
		return "Contact [RelationShipID=" + RelationShipID + ", userID=" + user + ", friendID=" + friendID + "]";
	}
	
	
}
