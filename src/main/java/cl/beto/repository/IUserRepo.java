package cl.beto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.beto.model.User;

public interface IUserRepo extends JpaRepository<User, Integer>{
	
	User findById(int id);
	
	User findByEmailAndPassword(String email, String password);
	
	List<User> findByUsernameStartsWith(String username);
	
}
