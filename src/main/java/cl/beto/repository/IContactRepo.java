package cl.beto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import cl.beto.model.Contact;
import cl.beto.model.User;

public interface IContactRepo extends JpaRepository<Contact, Integer> {

	@Query(value = "SELECT c.friendID FROM Contact c WHERE c.user = ?1")
	public List<Integer> findByUserId(User user);
	
	
	
}
