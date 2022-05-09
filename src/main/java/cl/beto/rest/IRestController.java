package cl.beto.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cl.beto.model.Contact;
import cl.beto.model.User;
import cl.beto.repository.*;

@RestController	
@RequestMapping("/api")
public class IRestController {

	@Autowired
	private IUserRepo usersRepo;
	
	@Autowired
	private IContactRepo contactRepo;

	@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
	@GetMapping(value = "/:{email}:{password}")
	public User userAcces(@PathVariable("email") String email, @PathVariable("password") String password) {
		User user = usersRepo.findByEmailAndPassword(email, password);
		
		return user;
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
	@GetMapping(value = "/search:{texto}")
	public List<User> listContactSearch(@PathVariable("texto") String texto) {
		if(!texto.isEmpty()) {
			return usersRepo.findByUsernameStartsWith(texto);
		}

		
		return null;
	}
	
	public User listUsers(int id) {
		return usersRepo.findById(id);
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
	@PostMapping
	public void insertUser(@RequestBody User user) {
		
		usersRepo.save(user);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public void Eliminar(@PathVariable("id") Integer id) {
		usersRepo.deleteById(id);
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.PUT})
	@PutMapping(value="/users:{id}/update")
	public void updateUser(@RequestBody User user) {
		
		usersRepo.save(user);
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
	@GetMapping(value = "/{id}/contactos")
	public List<User> contactList(@PathVariable("id") int id) {
		User user = listUsers(id);
		List<Integer> listaIdContactos = contactRepo.findByUserId(user);
		List<User> listaContactos = new ArrayList<User>();
		
		for(Integer lista: listaIdContactos) {
			listaContactos.add(listUsers(lista));
		}
		
		return listaContactos;
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
	@PostMapping(value = "/{id}/agregarContacto")
	public void insertContact(@PathVariable("id") int id, @RequestBody String idContactoS) {
		int idContacto = Integer.parseInt(idContactoS.substring(0,1));
		User user = listUsers(id);
		Contact newContact = new Contact();
		
		newContact.setUser(user);
		newContact.setFriendID(idContacto);
		
		contactRepo.save(newContact);
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
    @GetMapping(value = "/users:{id}/getProfileimg" , produces = MediaType.IMAGE_PNG_VALUE)
    public void getProfileImageUser(HttpServletResponse response, @PathVariable("id") int id){
		FileInputStream imgFile;
		
		try {
			imgFile = new FileInputStream("C:/Users/rober/OneDrive/Escritorio/Crud/dataBackend/profileimage-"+id+".png");
	        response.setContentType(MediaType.IMAGE_PNG_VALUE);
	        StreamUtils.copy(imgFile, response.getOutputStream());
	        imgFile.close();
		}catch(Exception e) {
			try {
				imgFile = new FileInputStream("C:/Users/rober/OneDrive/Escritorio/Crud/dataBackend/defaultuser.png");
				response.setContentType(MediaType.IMAGE_PNG_VALUE);
				StreamUtils.copy(imgFile, response.getOutputStream());
				imgFile.close();
			} catch (Exception e1 ) {
				System.out.println("Error Al Obtener Imagen");
			}
		}
    }
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
    @GetMapping(value = "/users:{id}/infoUser")
	public User getInfoUser(@PathVariable("id") int id) {
		User user = usersRepo.findById(id);
		
		return user;
	}
	
	@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
	@PostMapping(value = "/users:{id}/setProfileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void setImageUser(@PathVariable("id") int id, @RequestPart("file") MultipartFile file, HttpServletResponse response) throws IOException { 
		Files.copy(file.getInputStream(), Paths.get("C:/Users/rober/OneDrive/Escritorio/Crud/dataBackend/profileimage-"+id+".png"), StandardCopyOption.REPLACE_EXISTING);
	}
	
}