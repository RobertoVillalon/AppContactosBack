package cl.beto.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.beto.entity.User;
import cl.beto.repository.IUserRepo;
import cl.beto.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserRepo usersRepo;

	@Override
	public void insertUser(User user) {
		usersRepo.save(user);
	}

	@Override
	public void updateUser(User user) {
		usersRepo.save(user);
	}

	@Override
	public Optional<User> getUser(int idUser) {
		Optional<User> user = usersRepo.findById(idUser);

		return user;
	}

	@Override
	public void deleteUser(User user) {
		usersRepo.delete(user);
	}

	@Override
	public User getAcces(String email, String password) {
		User user = usersRepo.findByEmailAndPassword(email, password);
		
		return user;
	}

	@Override
	public List<User> getUserWithSearchText(String searchText) {
		if(!searchText.isEmpty()) {
			return usersRepo.findByUsernameStartsWith(searchText);
		}
		
		return null;
	}
	

	@Override
	public void getProfileImageUser(int idUser, HttpServletResponse response) {
		FileInputStream imgFile;
		
		try {
			imgFile = new FileInputStream("C:/Users/rober/OneDrive/Escritorio/Crud/dataBackend/profileimage-"+idUser+".png");
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
				e1.printStackTrace();
			}
		}
	}

	@Override
	public boolean setProfileImageUser(int idUser, MultipartFile imagen, HttpServletResponse response) {
		try {
			Files.copy(imagen.getInputStream(), Paths.get("C:/Users/rober/OneDrive/Escritorio/Crud/dataBackend/profileimage-"+idUser+".png"), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
