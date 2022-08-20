package cl.beto.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import cl.beto.entity.User;
import cl.beto.exception.UserException;
import cl.beto.security.JwtTokenProvider;
import cl.beto.service.IContactService;
import cl.beto.service.IUserService;

@RestController	
@RequestMapping("/api")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IContactService contactService;
	
    @Autowired
    JwtTokenProvider tokenProvider;
	
	@GetMapping(value = "/infoUser:{jwt}")
	public ResponseEntity<User> userInfo(@PathVariable("jwt") String jwt){
		User newUser = userService.getUser(tokenProvider.getUserIdFromJWT(jwt)).orElseThrow(() -> new UserException("El usuario no existe"));
		
		return ResponseEntity.ok(newUser);
	}
    
	@GetMapping(value = "/search:{texto}")
	public ResponseEntity<List<User>> usersSearch(@PathVariable("texto") String texto) {
		List<User> userList = userService.getUserWithSearchText(texto);
		
		if(userList != null) {
			return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		}
		
		return null;
	}
	
	public ResponseEntity<Optional<User>> listUsers(int id) {
		Optional<User> user = userService.getUser(id);
		
		return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> insertUser(@RequestBody User user) {
		userService.insertUser(user);
		
		return new ResponseEntity<>("The user was inserted successfully.", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> Eliminar(@RequestBody User user) {
		userService.deleteUser(user);
		
		return new ResponseEntity<>("The user was deleted successfully.", HttpStatus.OK);
	}
	
	@PutMapping(value="/users/{id}/update")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		
		return new ResponseEntity<>("The user was updated successfully.", HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/contactos")
	public ResponseEntity<List<User>> contactList(@PathVariable("id") int id) {
		List<Integer> contactList = contactService.getContactsByUser(userService.getUser(id).orElseThrow(() -> new UsernameNotFoundException("Error al conseguir el usuario para"
				+ "la lista de usuarios")));
		List<User> userList = new ArrayList<User>();
		
		for(Integer contact: contactList) {
			userList.add(userService.getUser(contact).orElseThrow(() -> new UsernameNotFoundException("Error al conseguir el usuario para"
					+ "la lista de usuarios")));
		}
		
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/agregarContacto")
	public ResponseEntity<Object> insertContact(@PathVariable("id") int id, @RequestBody String idContactoS) {
		int idContact = Integer.parseInt(idContactoS.substring(0,1));
		User user = userService.getUser(id).orElseThrow(() -> new UsernameNotFoundException("Error al conseguir el usuario para la insercion"));
		
		contactService.insertContact(user, idContact);
		
		return new ResponseEntity<Object>("the contact was added successfully",HttpStatus.OK);
	}
	
    @GetMapping(value = "/users/images/{id}/getProfileimg" , produces = MediaType.IMAGE_PNG_VALUE)
    public void getProfileImageUser(HttpServletResponse response, @PathVariable("id") int id){
		userService.getProfileImageUser(id, response);
		
    }
	
	@PostMapping(value = "/users/images/{id}/setProfileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> setProfileImageUser(@PathVariable("id") int id, @RequestPart("file") MultipartFile file, HttpServletResponse response) throws IOException {
		
		if(userService.setProfileImageUser(id, file, response)) {
			return new ResponseEntity<Object>("The image was set successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<Object>("There was an error modifying the image", HttpStatus.BAD_GATEWAY);
	}
}