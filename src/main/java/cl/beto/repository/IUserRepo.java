package cl.beto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.beto.entity.User;

public interface IUserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findById(int id);
	
	User findByEmailAndPassword(String email, String password);
	
	List<User> findByUsernameStartsWith(String username);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
}
