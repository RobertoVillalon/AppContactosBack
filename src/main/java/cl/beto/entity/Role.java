package cl.beto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLInsert;

import cl.beto.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Role")
@NoArgsConstructor
public class Role {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="roleID") @Getter @Setter
	private int roleID;
	
    @Enumerated(EnumType.STRING) @NaturalId @Column(length = 60) @Getter @Setter
    private RoleName name;

	public Role(RoleName name) {
		this.name = name;
	}
    
}
