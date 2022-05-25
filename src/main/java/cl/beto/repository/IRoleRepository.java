package cl.beto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.beto.entity.Role;
import cl.beto.enums.RoleName;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(RoleName roleUser);
	
}
