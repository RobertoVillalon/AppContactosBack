package cl.beto.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.beto.entity.User;
import lombok.*;

@AllArgsConstructor
public class UserPrincipal implements UserDetails{
	@Getter
    private int id;
	
	@Getter
    private String username;
	
	@Getter
    @JsonIgnore
    private String email;
	
	@Getter
    @JsonIgnore
    private String password;
	
	@Getter
    private Collection<? extends GrantedAuthority> authorities;
    
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserID(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || (obj.getClass() != obj.getClass()))
			return false;
		UserPrincipal other = (UserPrincipal) obj;
			return Objects.equals(id, other.id);
	}
	
	

}
