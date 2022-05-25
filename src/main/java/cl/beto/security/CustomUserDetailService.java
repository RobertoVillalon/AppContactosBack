package cl.beto.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.beto.entity.User;
import cl.beto.repository.IUserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	IUserRepo userRepo;

	@Override @Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> 
        	new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
		);
		
		return UserPrincipal.create(user);
	}
	
    @Transactional
    public UserDetails loadUserById(int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+ id));

        return UserPrincipal.create(user);
    }

}
