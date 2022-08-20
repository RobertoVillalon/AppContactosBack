package cl.beto.rest;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import cl.beto.entity.Role;
import cl.beto.entity.User;
import cl.beto.enums.RoleName;
import cl.beto.exception.AppException;
import cl.beto.exception.UserException;
import cl.beto.payloads.ApiResponse;
import cl.beto.payloads.LoginRequest;
import cl.beto.payloads.SignUpRequest;
import cl.beto.repository.IRoleRepository;
import cl.beto.repository.IUserRepo;
import cl.beto.security.JwtTokenProvider;
import cl.beto.service.IUserService;

@RestController
@RequestMapping("/api")
public class AuthController {
	private final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepo userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    IUserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        
        HashMap<String, Object> userData = new HashMap<String, Object>();
        
        userData.put("auth", jwt);
        userData.put("user", userService.getUser(tokenProvider.getUserIdFromJWT(jwt)).orElseThrow(() -> new UserException("Error al conseguir el usuario")));
        
        return ResponseEntity.ok(userData);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
